package dev.minhazav.camera2benchmark.apiprobe;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class CameraCharecteristicsRetriever {

    private static final String LOG_TAG = CameraCharecteristicsRetriever.class.getCanonicalName();

    public enum CameraFacing {
        FRONT_CAMERA,
        BACK_CAMERA
    }

    private final CameraManager cameraManager;

    public CameraCharecteristicsRetriever(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    @Nullable
    public String getAvailableSupport(CameraFacing cameraFacing) {
        CameraCharacteristics cameraCharacteristics = getCameraCharecteristics(cameraFacing);
        if (cameraCharacteristics == null) {
            return null;
        }

        int supportLevel = cameraCharacteristics
                .get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        return Camera2SupportHardwareLevelMapping.getSupportLevel(supportLevel);
    }

    public ArrayList<String> getAvailableEffects(CameraFacing cameraFacing) {
        ArrayList<String> result = new ArrayList<>();
        CameraCharacteristics cameraCharacteristics = getCameraCharecteristics(cameraFacing);
        if (cameraCharacteristics == null) {
            return result;
        }

        int[] effects = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS);
        for (int effect : effects) {
            String effectName = Camera2ControlEffectsMapping.getEffectName(effect);
            if (effectName != null) {
                result.add(effectName);
            }
        }

        return result;
    }

    @Nullable
    private CameraCharacteristics getCameraCharecteristics(CameraFacing cameraFacing) {
        try {
            String cameraId = getCameraId(cameraFacing);
            if (cameraId != null) {
                return cameraManager.getCameraCharacteristics(cameraId);
            }
        } catch (Exception ex) {
            Log.w(LOG_TAG, ex);
        }

        return null;
    }

    @Nullable
    private String getCameraId(CameraFacing cameraFacing) {
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics
                        = cameraManager.getCameraCharacteristics(cameraId);

                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null) {
                    if (cameraFacing.equals(CameraFacing.BACK_CAMERA)
                        && facing == CameraCharacteristics.LENS_FACING_BACK) {
                        return cameraId;
                    }

                    if (cameraFacing.equals(CameraFacing.FRONT_CAMERA)
                        && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                        return cameraId;
                    }
                }
            }
        } catch (Exception ex) {
            // TODO(mebjas): fix this.
        }

        return null;
    }

    private static class Camera2ControlEffectsMapping {
        private static Map<Integer, String> map = new HashMap<Integer, String>() {{
            put(CameraMetadata.CONTROL_EFFECT_MODE_OFF, "OFF");
            put(CameraMetadata.CONTROL_EFFECT_MODE_MONO, "MONO");
            put(CameraMetadata.CONTROL_EFFECT_MODE_NEGATIVE, "NEGATIVE");
            put(CameraMetadata.CONTROL_EFFECT_MODE_SOLARIZE, "SOLARIZE");
            put(CameraMetadata.CONTROL_EFFECT_MODE_SEPIA, "SEPIA");
            put(CameraMetadata.CONTROL_EFFECT_MODE_POSTERIZE, "POSTERIZE");
            put(CameraMetadata.CONTROL_EFFECT_MODE_WHITEBOARD, "WHITEBOARD");
            put(CameraMetadata.CONTROL_EFFECT_MODE_BLACKBOARD, "BLACKBOARD");
            put(CameraMetadata.CONTROL_EFFECT_MODE_AQUA, "AQUA");
        }};

        @Nullable
        public static String getEffectName(int key) {
            return map.get(key);
        }
    }

    private static class Camera2SupportHardwareLevelMapping {
        private static Map<Integer, String> map = new HashMap<Integer, String>() {{
            put(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED, "LIMITED");
            put(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY, "LEGACY");
            put(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_3, "LEVEL 3");
            put(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL, "EXTERNAL");
            put(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_FULL, "FULL");
        }};

        @Nullable
        public static String getSupportLevel(int key) {
            return map.get(key);
        }
    }
}
