package com.xiaomi.camera.core;

import android.hardware.camera2.params.OutputConfiguration;
import android.media.Image;
import android.media.ImageReader;
import android.os.Parcelable;
import com.android.camera.log.Log;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.ImageProcessor;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.FrameData;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.ArrayList;
import java.util.List;

public class DualCameraProcessor extends ImageProcessor {
    /* access modifiers changed from: private */
    public static final String TAG = "DualCameraProcessor";

    DualCameraProcessor(ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback, boolean z, BufferFormat bufferFormat) {
        super(imageProcessorStatusCallback, z, bufferFormat);
    }

    private void processCaptureResult(ICustomCaptureResult iCustomCaptureResult, Image image, int i) {
        Parcelable results = iCustomCaptureResult.getResults();
        String str = TAG;
        Log.d(str, "processCaptureResult: cameraMetadataNative = " + results);
        String str2 = TAG;
        Log.d(str2, "processCaptureResult: processFrame image -- " + i);
        FrameData frameData = new FrameData(i, iCustomCaptureResult.getSequenceId(), iCustomCaptureResult.getFrameNumber(), results, iCustomCaptureResult.getParcelRequest(), image);
        frameData.setFrameCallback(new FrameData.FrameStatusCallback() {
            public void onFrameImageClosed(Image image) {
                String access$000 = DualCameraProcessor.TAG;
                Log.d(access$000, "onFrameImageClosed: " + image);
                ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback = DualCameraProcessor.this.mImageProcessorStatusCallback;
                if (imageProcessorStatusCallback != null) {
                    imageProcessorStatusCallback.onOriginalImageClosed(image);
                }
                ImagePool.getInstance().releaseImage(image);
            }
        });
        this.mTaskSession.processFrame(frameData, new TaskSession.FrameCallback() {
            public void onFrameProcessed(int i, String str, Object obj) {
                String access$000 = DualCameraProcessor.TAG;
                Log.d(access$000, "onFrameProcessed: " + i);
            }
        });
    }

    public List<OutputConfiguration> configOutputConfigurations(BufferFormat bufferFormat) {
        ArrayList arrayList = new ArrayList();
        this.mEffectImageReaderHolder = ImageReader.newInstance(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight(), bufferFormat.getBufferFormat(), getImageBufferQueueSize());
        this.mEffectImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                long timestamp = acquireNextImage.getTimestamp();
                PerformanceTracker.trackAlgorithmProcess("[  EFFECT]", 1);
                String access$000 = DualCameraProcessor.TAG;
                Log.d(access$000, "onImageAvailable: effectImage received: " + timestamp);
                Image queueImageToPool = DualCameraProcessor.this.queueImageToPool(ImagePool.getInstance(), acquireNextImage);
                acquireNextImage.close();
                DualCameraProcessor.this.dispatchFilterTask(new ImageProcessor.FilterTaskData(queueImageToPool, 0, true));
                DualCameraProcessor.this.onProcessImageDone();
            }
        }, getImageReaderHandler());
        arrayList.add(new OutputConfiguration(0, this.mEffectImageReaderHolder.getSurface()));
        this.mRawImageReaderHolder = ImageReader.newInstance(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight(), bufferFormat.getBufferFormat(), getImageBufferQueueSize());
        this.mRawImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                long timestamp = acquireNextImage.getTimestamp();
                PerformanceTracker.trackAlgorithmProcess("[     RAW]", 1);
                String access$000 = DualCameraProcessor.TAG;
                Log.d(access$000, "onImageAvailable: rawImage received: " + timestamp);
                Image queueImageToPool = DualCameraProcessor.this.queueImageToPool(ImagePool.getInstance(), acquireNextImage);
                acquireNextImage.close();
                DualCameraProcessor.this.dispatchFilterTask(new ImageProcessor.FilterTaskData(queueImageToPool, 1, true));
            }
        }, getImageReaderHandler());
        arrayList.add(new OutputConfiguration(1, this.mRawImageReaderHolder.getSurface()));
        this.mDepthImageReaderHolder = ImageReader.newInstance(bufferFormat.getBufferWidth() / 2, bufferFormat.getBufferHeight() / 2, 540422489, getImageBufferQueueSize());
        this.mDepthImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                PerformanceTracker.trackAlgorithmProcess("[   DEPTH]", 1);
                String access$000 = DualCameraProcessor.TAG;
                Log.d(access$000, "onImageAvailable: depthImage received: " + acquireNextImage.getTimestamp());
                ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback = DualCameraProcessor.this.mImageProcessorStatusCallback;
                if (imageProcessorStatusCallback != null) {
                    imageProcessorStatusCallback.onImageProcessed(acquireNextImage, 2, false);
                }
                acquireNextImage.close();
                DualCameraProcessor.this.mNeedProcessDepthImageSize.getAndDecrement();
                DualCameraProcessor.this.tryToStopWork();
            }
        }, getImageReaderHandler());
        arrayList.add(new OutputConfiguration(2, this.mDepthImageReaderHolder.getSurface()));
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public boolean isIdle() {
        return this.mNeedProcessNormalImageSize.get() == 0 && this.mNeedProcessRawImageSize.get() == 0 && this.mNeedProcessDepthImageSize.get() == 0;
    }

    /* access modifiers changed from: package-private */
    public void processImage(List<CaptureData.CaptureDataBean> list) {
        if (list == null || list.size() == 0) {
            Log.w(TAG, "processImage: dataBeans is empty!");
            return;
        }
        onProcessImageStart();
        for (CaptureData.CaptureDataBean next : list) {
            ICustomCaptureResult result = next.getResult();
            PerformanceTracker.trackAlgorithmProcess("[ORIGINAL]", 0);
            processCaptureResult(result, next.getMainImage(), 0);
            processCaptureResult(result, next.getSubImage(), 1);
        }
    }
}
