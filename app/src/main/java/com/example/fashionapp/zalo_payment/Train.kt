package com.example.fashionapp.zalo_payment

//class TrainDataFragment : Fragment(), OnReceiverResult {
//
//    private lateinit var binding: FragmentTrainDataBinding
//    private val trainDataViewModel by viewModel<TrainDataViewModel>()
//    private val args by navArgs<TrainDataFragmentArgs>()
//    private var mSocket: Socket? = null
//    private val staffData by inject<StaffData>()
//    private val sharedData by inject<SharedData>()
//    private var faceId = EngineWrapper.HEAD_POSE_NORMAL
//    private var stateTrainDataTrain:StateTrainDataChange? = null
//    private var uploadSuccess = false
//    private var count = 0
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentTrainDataBinding.inflate(inflater, container, false).apply {
//            viewModel = trainDataViewModel
//            lifecycleOwner = viewLifecycleOwner
//            faceId = this@TrainDataFragment.faceId
//            isStaff = args.isStaff
//        }
//        return binding.root
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupEvent()
//        createSocket()
//    }
//
//    private fun setupEvent() {
//        trainDataViewModel.closeClickEvent.observe(this, EventObserver {
//            findNavController().popBackStack()
//        })
//        trainDataViewModel.switchClickEvent.observe(this, EventObserver {
//            binding.ekycComponentView.switchCamera()
//        })
//    }
//
//    private fun createSocket() {
//        val options = IO.Options().apply {
//            forceNew = true
//            reconnectionAttempts = Integer.MAX_VALUE;
//            reconnection = true
//        }
//        mSocket = IO.socket(Constants.SOCKET_URI_DEV, options)
//        mSocket?.on(Socket.EVENT_CONNECT) {
//            Log.e("Debug", "socket connect")
//            initSession()
//        }
//        mSocket?.on(Socket.EVENT_DISCONNECT) {
//            Log.e("Debug", "socket disconnect")
//        }
//        mSocket?.on(Socket.EVENT_CONNECT_ERROR) {
//            Log.e("Debug" +
//                    "", "socket EVENT_CONNECT_ERROR ${it.toString()}")
//        }
//        mSocket?.on("is_ready") {
//            val jsonObject = it[0] as JSONObject
//            if (jsonObject.has("data")) {
//                getFaceWithPose()
//            }else{
//                Log.e("error_is_ready",jsonObject.toString())
//                requireActivity().showAlertDialog(message = jsonObject.getString("message"),positiveText = "OK",
//                    onPositiveClick = { _, _ ->
//                        findNavController().popBackStack()
//                    })
//            }
//        }
////        mSocket?.on("finish_upload_face") {
////            Log.e("oke","error_finish_upload_face")
////            val jsonObject = it[0] as JSONObject
////            if (jsonObject.getString("status")=="success"){
//////                getFaceWithPose()
////            }
////            else    Log.e("error","error_finish_upload_face")
////        }
//        mSocket?.on("finish_align_face"){
//            val jsonObject = it[0] as JSONObject
//            Log.e("finish_align_face",jsonObject.toString())
//            if(jsonObject.getString("status")=="success"){
//                stateTrainDataTrain?.changeState()
//                trainDataViewModel.updateStateTrain(4)
//                if(!uploadSuccess){
//                    uploadSuccess=true
//                    requireActivity().showAlertDialog(title = resources.getString(R.string.train_data_success),
//                        message = resources.getString(if (args.isStaff) R.string.train_data_success_message else R.string.train_data_success_message_student),
//                        resources.getString(R.string.ok),
//                        onPositiveClick = { p0, p1 -> findNavController().popBackStack() })
//                }
//            }else if(jsonObject.getString("status")=="fail" && jsonObject.getInt("code")==Constants.duplicateFaceTrainer){
//                Log.e("error","error")
//                trainDataViewModel.updateStateTrain(-1)
//                if(!uploadSuccess){
//                    uploadSuccess = true
//                    requireActivity().showAlertDialog(title = "Đã có trong cơ sở dữ liệu",
//                        message = "Gương mặt đã có trong cơ sơ dữ liệu. Vui lòng quét gương mặt mới.",
//                        positiveText = "Tiếp tục quét",
//                        onPositiveClick = { dialog,p1 ->
//                            initSession()
//                            trainDataViewModel.updateStateTrain(0)
//                            binding.faceId = EngineWrapper.HEAD_POSE_NORMAL
//                            dialog.cancel()
//                            uploadSuccess = false
//                        },
//                        negativeText = "Báo lỗi",
//                        onNegativeClick = { dialog,p1 ->
//                            val action = ReportErrorFragmentDirections.actionGlobalReportErrorFragment()
//                            findNavController().navigate(action)
//                        }
//                    )
//                }
//            }else if(jsonObject.getString("status")=="fail" && jsonObject.getInt("code")==Constants.faceFake){
//                trainDataViewModel.updateStateTrain(-1)
//                if(!uploadSuccess){
//                    uploadSuccess = true
//                    requireActivity().showAlertDialog(title = "Gương mặt không hợp lệ",
//                        message = "Vui lòng quét gương mặt khác",
//                        positiveText = "Tiếp tục quét",
//                        onPositiveClick = { dialog,p1 ->
//                            initSession()
//                            trainDataViewModel.updateStateTrain(0)
//                            binding.faceId = EngineWrapper.HEAD_POSE_NORMAL
//                            dialog.cancel()
//                            uploadSuccess = false
//                        },
//                        negativeText = "Báo lỗi",
//                        onNegativeClick = { dialog,p1 ->
//                            val action = ReportErrorFragmentDirections.actionGlobalReportErrorFragment()
//                            findNavController().navigate(action)
//                        }
//                    )
//                }
//
//            }else if(jsonObject.getString("status")=="fail"){
//                Log.e("upload",uploadSuccess.toString())
//                if(!uploadSuccess){
//                    uploadSuccess = true
//                    requireActivity().showAlertDialog(title = "Gương mặt không hợp lệ",
//                        message = "Vui lòng quét gương mặt khác",
//                        positiveText = "Tiếp tục quét",
//                        onPositiveClick = { dialog,p1 ->
//                            initSession()
//                            trainDataViewModel.updateStateTrain(0)
//                            binding.faceId = EngineWrapper.HEAD_POSE_NORMAL
//                            dialog.cancel()
//                            uploadSuccess = false },
//                        negativeText = "Báo lỗi",
//                        onNegativeClick = { dialog,p1 ->
//                            val action = ReportErrorFragmentDirections.actionGlobalReportErrorFragment()
//                            findNavController().navigate(action)
//                        }
//                    )
//                }
//
//            }
//        }
//        mSocket?.connect()
//    }
//
//    private fun getFaceWithPose() {
//        binding.ekycComponentView.getFaceWithPose(EngineWrapper.HEAD_POSE_NORMAL, this)
//    }
////
////    private fun nextPose() {
////        if (faceId == EngineWrapper.HEAD_POSE_NORMAL) {
////            trainDataViewModel.updateStateTrain(1)
////            faceId = EngineWrapper.HEAD_POSE_FACE_LEFT
////            getFaceWithPose()
////        } else if (faceId == EngineWrapper.HEAD_POSE_FACE_LEFT) {
////            trainDataViewModel.updateStateTrain(2)
////            faceId = EngineWrapper.HEAD_POSE_FACE_RIGHT
////            getFaceWithPose()
////        } else {
////            trainDataViewModel.updateStateTrain(3)
////            finish()
////        }
////        binding.faceId = faceId
////    }
//
//    private fun finish() {
//        stateTrainDataTrain?.changeState()
//    }
//
//    private fun initSession() {
//        val jsonObject = JSONObject()
//        jsonObject.put("session_id", sharedData.getToken())
//        jsonObject.put("customer", "junsport")
//        jsonObject.put("uid", args.uid)
//        jsonObject.put("role", if (args.isStaff) "staff" else "student")
//        jsonObject.put("type","train_face")
//        mSocket?.emit("init_session", jsonObject)
//    }
//
//    private fun emitFace(base64: String) {
//        val jsonObject = JSONObject()
//        jsonObject.put("image", base64)
//        jsonObject.put("poseKey","look_straight")
////        var poseKey = ""
////        when (faceId) {
////            EngineWrapper.HEAD_POSE_NORMAL -> {
////                poseKey = "look_straight"
////            }
////            EngineWrapper.HEAD_POSE_FACE_LEFT -> {
////                poseKey = "turn_left"
////                trainDataViewModel.stateTrain.value = 1
////            }
////            else -> {
////                poseKey = "turn_right"
////                trainDataViewModel.stateTrain.value = 2
////            }
////        }
////        jsonObject.put("poseKey", poseKey)
//        mSocket?.emit("upload_face", jsonObject)
//        Log.e("count",(++count).toString())
//        if (!uploadSuccess){
//            CoroutineScope(Dispatchers.IO).launch {
//                delay(100)
//                getFaceWithPose()
//            }
//        }
//
//    }
//
//    override fun onReceiverResult(data: ByteArray, faceBox: FaceBox) {
//        val out = ByteArrayOutputStream()
//        val yuvImage = YuvImage(data, ImageFormat.NV21, 640, 480, null)
//        yuvImage.compressToJpeg(Rect(0, 0, 640, 480), 100, out)
//        val imageBytes: ByteArray = out.toByteArray()
//        var source = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//        val matrix = Matrix().apply { postRotate(-90f) }
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
//        Log.e("recognize_face","success")
//        if (!uploadSuccess){
//            emitFace(encoded)
//        }
//
//    }
//
//    interface StateTrainDataChange{
//        fun changeState()
//    }
//}