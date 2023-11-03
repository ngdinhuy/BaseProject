package com.example.fashionapp.zalo_payment



//class CheckInFragment:Fragment(), OnReceiverResult, MyCallback {
//    private lateinit var binding : FragmentCheckinBinding
//    private val checkInViewModel : CheckInViewModel by viewModel()
//    private val args by navArgs<CheckInFragmentArgs>()
//    private var mSocket : Socket? = null
//    private val sharedData : SharedData by inject<SharedData>()
//    private val staffData : StaffData by inject<StaffData>()
//    private val faceId = EngineWrapper.HEAD_POSE_NORMAL
//    private lateinit var staffModel: StaffModel
//    private lateinit var name:String
//    private lateinit var urlAvatar:String
//    private lateinit var code:String
//    private val mainViewModel:MainViewModel by activityViewModels()
//    private lateinit var fusedLocationClient : FusedLocationProviderClient
//    private var currentLocation: Location? = null
//    private var facecheck_time : String = ""
//    private var  countUpload = 1
//    private var uploadSuccess = false
//    var callback:MyCallback?=null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View?{
//        binding = FragmentCheckinBinding.inflate(inflater,container,false).apply {
//            lifecycleOwner = viewLifecycleOwner
//            viewmodel = checkInViewModel
//            classModel = args.classModel
//        }
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        setUpEvent()
//        creatSocket()
//        getCurrentLocation(this)
//    }
//
//    private fun creatSocket() {
//        val options = IO.Options().apply {
//            forceNew = true
//            reconnectionAttempts = Integer.MAX_VALUE
//            reconnection = true
//        }
//        mSocket = IO.socket(Constants.SOCKET_URI_DEV,options)
//        mSocket?.on(Socket.EVENT_CONNECT){
//            Log.e("Debug","socket connect")
//            //init session after getting location
//            getCurrentLocation(this)
//        }
//        mSocket?.on(Socket.EVENT_DISCONNECT){
//            Log.e("Debug","socket disconnect")
//        }
//        mSocket?.on(Socket.EVENT_CONNECT_ERROR){
//            Log.e("Debug" + "",
//                "socket EVENT_CONNECT_ERROR ${it.toString()}")
//        }
//        mSocket?.on("is_ready"){
//            val jsonObject = it[0] as JSONObject
//            if(jsonObject.has("data")){
//                getFaceWithPose()
//                if(!args.isStaff){
//                    passIDStudentToDetailFragment()
//                }
//            }else{
//                Log.e("error_is_ready",jsonObject.toString())
//                requireActivity().showAlertDialog(message = jsonObject.getString("message"),positiveText = "OK",
//                    onPositiveClick = {_,_ ->
//                        findNavController().popBackStack()
//                    })
//            }
//        }
//        mSocket?.on("scan_face"){
//            if(countUpload < 3){
//                countUpload++
//                CoroutineScope(Dispatchers.IO).launch {
//                    delay(300)
//                    getFaceWithPose()
//                }
//            }
//        }
//        mSocket?.on("finish_scan_face"){
//            val jsonObject = it[0] as JSONObject
//            if (jsonObject.optString("status")=="success"){
//                checkInViewModel.updateStateCheckin(1)
//                Log.e("finish_scan_face",jsonObject.toString())
//                val data = jsonObject.getJSONObject("data")
//                name = data.getString("name")
//                code = data.getString("code")
//                if(args.isStaff){
//                    urlAvatar = data.getString("avatar")
//                }
//                checkInViewModel.updateName(name)
//                checkInViewModel.updateRecognizeFace(1)
//                checkInViewModel.updateCode(code)
//                if(!args.areManyStudent)
//                    checkInViewModel.updateCheckInSuccessEvent()
//            }
//            else{
//                if(countUpload<3){
//                    getFaceWithPose()
//                    countUpload++
//                }else{
//                    checkInViewModel.updateStateCheckin(-1)
//                    Log.e("error_finish_scan_face",jsonObject.toString())
//                    when {
//                        jsonObject.optInt("code")==Constants.faceRecognizedNotFound -> {
//                            val data = jsonObject.getJSONObject("data")
//                            if (!uploadSuccess){
//                                uploadSuccess = true
//                                requireActivity().showAlertDialog(title = "Không nhận diện được khuôn mặt",
//                                    message = "Hãy thử check-in lại 2 lần trước khi xác nhận đây là lỗi và gửi báo cáo lỗi.",
//                                    positiveText = "Xem chi tiết", onPositiveClick = { dialog,_->
//                                        dialog.cancel()
//                                        reCheckin()
//                                    },
//                                    negativeText = "Báo lỗi", onNegativeClick = {_,_->
//                                        val action = ReportErrorFragmentDirections.actionGlobalReportErrorFragment(data.getString("error_id"),data.getString("error_type"),data.getInt("error_code"))
//                                        findNavController().navigate(action)
//                                    }
//                                )
//                            }
//
//                        }
//                        jsonObject.optInt("code")==Constants.faceRecognizedWrongLocation -> {
//                            if(!uploadSuccess){
//                                uploadSuccess = true
//                                val data = jsonObject.getJSONObject("data")
//                                requireActivity().showAlertDialog(title = "Vị trí không đạt điều kiện",
//                                    message = "Hãy di chuyển tới gần sân hơn hoặc lấy lại vị trí và thử lại.",
//                                    positiveText = "Xem chi tiết", onPositiveClick = { dialog,_->
//                                        dialog.cancel()
//                                        reCheckin()
//                                    },
//                                    negativeText = "Báo lỗi", onNegativeClick = {_,_->
//                                        val action = ReportErrorFragmentDirections.actionGlobalReportErrorFragment(data.getString("error_id"),data.getString("error_type"),data.getInt("error_code"))
//                                        findNavController().navigate(action)
//                                    }
//                                )
//                            }
//
//                        }
//                        jsonObject.optInt("code")==Constants.faceFake ->{
//                            if (!uploadSuccess){
//                                uploadSuccess = true
//                                val data = jsonObject.getJSONObject("data")
//                                requireActivity().showAlertDialog(title = "Gương mặt không hợp lệ",
//                                    message = "Vui lòng quét gương mặt khác.",
//                                    positiveText = "Xem chi tiết", onPositiveClick = { dialog,_->
//                                        dialog.cancel()
//                                        reCheckin()
//                                    },
//                                    negativeText = "Báo lỗi", onNegativeClick = {_,_->
//                                        val action = ReportErrorFragmentDirections.actionGlobalReportErrorFragment(data.getString("error_id"),data.getString("error_type"),data.getInt("error_code"))
//                                        findNavController().navigate(action)
//                                    }
//                                )
//                            }
//                        }
//                        else -> {
//                            if (!uploadSuccess){
//                                uploadSuccess = true
//                                requireActivity().showAlertDialog(title = "Error",
//                                    message =jsonObject.optInt("code").toString() + ": " + jsonObject.optString("message"),
//                                    positiveText = "Thử lại",
//                                    onPositiveClick = {dialog,_ ->
//                                        reCheckin()
//                                    },
//                                    negativeText = "Đóng", onNegativeClick = {dialog,_->
//                                        dialog.cancel()
//                                        findNavController().popBackStack()
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        mSocket?.connect()
//    }
//
//    private fun reCheckin(){
//        countUpload = 1
//        initSession()
//        checkInViewModel.updateStateCheckin(0)
//        uploadSuccess = false
//    }
//    private fun getFaceWithPose() {
//        binding.ekycComponentView.getFaceWithPose(faceId,this)
//    }
//
//
//    private fun initSession() {
//        if (getDistance(currentLocation!!.latitude,currentLocation!!.longitude,args.classModel.displayLatitude(), args.classModel.displayLongitude()) < Constants.maximumDistance){
//            val jsonObject = JSONObject()
//            jsonObject.put("session_id",sharedData.getToken())
//            jsonObject.put("customer","junsport")
//            jsonObject.put("role",if (args.isStaff) "staff" else "student")
//            if(!args.areManyStudent)
//                jsonObject.put("type","compare_face")
//            else
//                jsonObject.put("type","face_check")
//            if(!args.isStaff){
//                jsonObject.put("uid",args.studentID)
//            }
//            jsonObject.put("timekeeper_id",args.classModel.displayTimekeeperID())
//            jsonObject.put("class_id",args.classModel.idClass)
//            jsonObject.put("latitude", currentLocation?.latitude)
//            jsonObject.put("longitude",currentLocation?.longitude)
//            mSocket?.emit("init_session",jsonObject)
//            Log.e("init_session_json",jsonObject.toString())
//        }
//    }
//
//    private fun setUpEvent() {
//        checkInViewModel.clickButtonCancelEvent.observe(this,EventObserver{
//            findNavController().popBackStack()
//        })
//        checkInViewModel.checkInSuccessEvent.observe(this, EventObserver{
//            if(args.isStaff){
//                args.classModel.completeCheck()
//                val action = CheckInFragmentDirections.actionCheckInFragmentToCheckInSuccessFragment(args.classModel,name, urlAvatar)
//                findNavController().navigate(action)
//            }else{
//                findNavController().popBackStack()
//            }
//        })
//        checkInViewModel.switchCamearaEvent.observe(this,EventObserver{
//            binding.ekycComponentView.switchCamera()
//        })
//    }
//
//
//    override fun onReceiverResult(data: ByteArray, faceBox: FaceBox) {
//        val out = ByteArrayOutputStream()
//        val yuvImage = YuvImage(data, ImageFormat.NV21, 640, 480, null)
//        yuvImage.compressToJpeg(Rect(0, 0, 640, 480), 100, out)
//        val imageBytes: ByteArray = out.toByteArray()
//        var source = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//        var matrix = Matrix().apply { postRotate(-90f) }
//        source = Bitmap.createBitmap(
//            source, 0, 0, source.width,
//            source.height, matrix, true
//        )
//        source = Bitmap.createBitmap(
//            source, (source.width - faceBox.right)-(faceBox.right - faceBox.left)*0.1.toInt(), faceBox.top-(faceBox.bottom - faceBox.top)*0.2.toInt(), (faceBox.right - faceBox.left)*1.2.toInt(),
//            (faceBox.bottom - faceBox.top)*1.2.toInt(), null, true
//        )
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        source.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
//        emitFace(encoded)
//        Log.e("recognize_face","success")
//    }
//
//    private fun emitFace(base64: String) {
//        val jsonObject = JSONObject()
//        jsonObject.put("image",base64)
//        mSocket?.emit("scan_face",jsonObject)
//    }
//
//    private fun passIDStudentToDetailFragment(){
//        mainViewModel.idStudentCheckin.postValue( Event(args.studentID))
//    }
//
//    private fun checkLocationPermission():Boolean{
//        return !(ActivityCompat.checkSelfPermission(
//            requireContext(),
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//            requireContext(),
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED)
//    }
//
//    private fun requestLocationPermission(){
//        ActivityCompat.requestPermissions(
//            requireActivity(),
//            arrayOf(
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            )
//            ,
//            1000
//        )
//    }
//    private fun getCurrentLocation(myCallback: MyCallback){
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        if(checkLocationPermission()){
//            fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, object : CancellationToken(){
//                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken  = CancellationTokenSource().token
//
//                override fun isCancellationRequested(): Boolean  = false
//
//            }).addOnSuccessListener {
//                if (it == null){
//                    requireContext().makeToast("Không thể lấy vị trí")
//                }else{
//                    currentLocation = it
//                    myCallback.initSession(it.latitude.toString(),it.longitude.toString())
//                }
//
//            }
//        }
//    }
//
//
//    override fun initSession(latitude: String, longitude: String) {
//        if (getDistance(latitude.toDouble(),longitude.toDouble(),args.classModel.displayLatitude(), args.classModel.displayLongitude()) > Constants.maximumDistance){
//            requireActivity().showAlertDialog(title = "Vị trí không đạt điều kiện",
//                message = "Hãy di chuyển tới gần sân hơn hoặc lấy lại vị trí và thử lại.",
//                positiveText = "Xem chi tiết", onPositiveClick = { dialog,_->
//                    dialog.cancel()
//                    reCheckin()
//                }
//            )
//            return
//        }
//        val jsonObject = JSONObject()
//        jsonObject.put("session_id",sharedData.getToken())
//        jsonObject.put("customer","junsport")
//        jsonObject.put("role",if (args.isStaff) "staff" else "student")
//        if(!args.areManyStudent)
//            jsonObject.put("type","compare_face")
//        else
//            jsonObject.put("type","face_check")
//        if(!args.isStaff){
//            jsonObject.put("uid",args.studentID)
//        }
//        jsonObject.put("timekeeper_id",args.classModel.displayTimekeeperID())
//        jsonObject.put("class_id",args.classModel.idClass)
//        jsonObject.put("latitude", latitude)
//        jsonObject.put("longitude",longitude)
//        mSocket?.emit("init_session",jsonObject)
//        Log.e("init_session_json",jsonObject.toString())
//    }
//    private fun getDistance(currentLat:Double, currentLong:Double, classLat:Double, classLong:Double):Double{
//        var R = 6371 //Radius of the earth in km
//        var dLat = deg2rad(currentLat-classLat)
//        var dLong = deg2rad(currentLong - classLong)
//        var a = sin(dLat/2) * sin(dLat/2) +
//                cos(deg2rad(currentLat)) * cos(deg2rad(currentLat)) *
//                sin(dLong/2) * sin(dLong/2)
//        var c = 2 * atan2(sqrt(a), sqrt(1-a))
//        return R * c * 1000
//    }
//    private fun deg2rad(deg:Double):Double = deg * (Math.PI / 180)
//}
//interface MyCallback{
//    fun initSession(latitude:String,longitude:String)
//}