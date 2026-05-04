#include <jni.h>
#include <string>
#include <openssl/evp.h>
#include <openssl/err.h>
#include <vector>
#include <sstream>
#include <iomanip>
#include <cstdlib>

using namespace std;

static const string KEY = "3f50274843b3f3673a060d949ed1e3e3";
static const string IV = "1201e3fb03653d23";
const unsigned char* key = reinterpret_cast<const unsigned char*>(KEY.c_str());
const unsigned char* iv  = reinterpret_cast<const unsigned char*>(IV.c_str());

string bytesToHexString(const std::vector<unsigned char>& bytes) {
    stringstream hexStream;
    hexStream << std::hex << std::setfill('0');
    for (unsigned char b : bytes) {
        hexStream << std::setw(2) << static_cast<int>(b);
    }
    return hexStream.str();
}

vector<unsigned char> hexStringToBytes(string& hex) {
    vector<unsigned char> bytes;
    for (size_t i = 0; i < hex.length(); i += 2) {
        string byteString = hex.substr(i, 2);
        unsigned char byte = static_cast<unsigned char>(strtol(byteString.c_str(), nullptr, 16));
        bytes.push_back(byte);
    }
    return bytes;
}

static string encryptString(string plainText) {
    EVP_CIPHER_CTX* ctx = EVP_CIPHER_CTX_new();
    if (!ctx) return "";

    if (1 != EVP_EncryptInit_ex(ctx, EVP_aes_256_cbc(), NULL, key, iv)) {
        EVP_CIPHER_CTX_free(ctx);
        return "";
    }

    vector <unsigned char> ciphertext (plainText.size() + EVP_MAX_BLOCK_LENGTH);
    int len = 0, ciphertext_len = 0;

    if (1 != EVP_EncryptUpdate(ctx, ciphertext.data(), &len, reinterpret_cast<const unsigned char*>(plainText.c_str()), plainText.size())) {
        EVP_CIPHER_CTX_free(ctx);
        return "";
    }
    ciphertext_len = len;

    if (1 != EVP_EncryptFinal_ex(ctx, ciphertext.data() + len, &len)) {
        EVP_CIPHER_CTX_free(ctx);
        return "";
    }
    ciphertext_len += len;
    EVP_CIPHER_CTX_free(ctx);
    ciphertext.resize(ciphertext_len);
    return bytesToHexString(ciphertext);
}

string decryptString(string cipherText) {
    // Bước 1: Chuyển hex → bytes
    vector<unsigned char> ciphertextBytes = hexStringToBytes(cipherText);
    EVP_CIPHER_CTX* ctx = EVP_CIPHER_CTX_new();
    if (!ctx) return "";

    // Bước 2: Khởi tạo context giải mã (AES-256-CBC, key, IV giống encrypt)
    if (1 != EVP_DecryptInit_ex(ctx, EVP_aes_256_cbc(), NULL, key, iv)) {
        EVP_CIPHER_CTX_free(ctx);
        return "";
    }

    // Bước 3: Cấp buffer cho plaintext (kích thước tối đa = ciphertext + block size)
    vector<unsigned char> plaintext(ciphertextBytes.size() + EVP_MAX_BLOCK_LENGTH);
    int len = 0, plaintext_len = 0;

    // Bước 4: Giải mã từng khối
    if (1 != EVP_DecryptUpdate(ctx, plaintext.data(), &len, ciphertextBytes.data(), ciphertextBytes.size())) {
        EVP_CIPHER_CTX_free(ctx);
        return "";
    }
    plaintext_len = len;

    // Bước 5: Finalize - xử lý padding, block cuối
    if (1 != EVP_DecryptFinal_ex(ctx, plaintext.data() + len, &len)) {
        EVP_CIPHER_CTX_free(ctx);
        return "";
    }
    plaintext_len += len;
    EVP_CIPHER_CTX_free(ctx);
    plaintext.resize(plaintext_len);
    return string(reinterpret_cast<char*>(plaintext.data()), plaintext_len);
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_baseproject_helper_DecryptHelper_getStringFromJNI(
        JNIEnv* env, jobject /* this */
        ) {
    std::string hello = "Hello from C++";
    return env ->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_baseproject_helper_DecryptHelper_encryptNative(
        JNIEnv* env,
        jobject /* this */,
        jstring plainText) {
    const char* input = env->GetStringUTFChars(plainText, nullptr);
    string plainTextStr = input;
    string result = encryptString(plainTextStr);

    env->ReleaseStringUTFChars(plainText, input);
    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_baseproject_helper_DecryptHelper_decryptNative(
        JNIEnv* env,
        jobject /* this */,
        jstring cipherText) {
    const char* input = env->GetStringUTFChars(cipherText, nullptr);

    string result = decryptString(input);

    env->ReleaseStringUTFChars(cipherText, input);
    return env->NewStringUTF(result.c_str());
}



