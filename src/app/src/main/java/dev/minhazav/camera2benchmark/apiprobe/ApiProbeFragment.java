package dev.minhazav.camera2benchmark.apiprobe;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.fragment.app.Fragment;

import dev.minhazav.camera2benchmark.R;

import static dev.minhazav.camera2benchmark.apiprobe.CameraCharecteristicsRetriever.CameraFacing.BACK_CAMERA;
import static dev.minhazav.camera2benchmark.apiprobe.CameraCharecteristicsRetriever.CameraFacing.FRONT_CAMERA;

public class ApiProbeFragment extends Fragment {

    private TextView probeLogs;

    private ApiProbeFragment() {
        // Required empty public constructor
    }

    public static ApiProbeFragment create() {
        return new ApiProbeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_api_probe, container, false);

        probeLogs = view.findViewById(R.id.probe_logs);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        CameraManager cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        loadData(cameraManager);
    }

    private void loadData(CameraManager cameraManager) {
        CameraCharecteristicsRetriever cameraCharecteristicsRetriever
                = new CameraCharecteristicsRetriever(cameraManager);
        // Get support level
        addProbeHeaderToUi("Support Level");
        addProbeResultToUi(
                "Back - " +cameraCharecteristicsRetriever.getAvailableSupport(BACK_CAMERA));
        addProbeResultToUi(
                "Front - " +cameraCharecteristicsRetriever.getAvailableSupport(FRONT_CAMERA));

        // Back camera available effects
        List<String> availableEffectsBackCam = cameraCharecteristicsRetriever
                .getAvailableEffects(BACK_CAMERA);
        addProbeHeaderToUi("BACK CAMERA AVAILABLE EFFECTS");
        if (availableEffectsBackCam.isEmpty()) {
            addProbeResultToUi("No effect found");
        } else {
            addProbeResultToUi(availableEffectsBackCam);
        }

        // Back camera available effects
        List<String> availableEffectsFrontCam = cameraCharecteristicsRetriever
                .getAvailableEffects(FRONT_CAMERA);
        addProbeHeaderToUi("FRONT CAMERA AVAILABLE EFFECTS");
        if (availableEffectsBackCam.isEmpty()) {
            addProbeResultToUi("No effect found");
        } else {
            addProbeResultToUi(availableEffectsFrontCam);
        }

        // Back camera available effects
        List<String> availableSceneModesBackCamera = cameraCharecteristicsRetriever
                .getColorSceneMode(BACK_CAMERA);
        addProbeHeaderToUi("BACK CAMERA AVAILABLE SCENE MODES");
        if (availableEffectsBackCam.isEmpty()) {
            addProbeResultToUi("No modes found");
        } else {
            addProbeResultToUi(availableSceneModesBackCamera);
        }

        // Back camera available effects
        List<String> availableSceneModesFrontCamera = cameraCharecteristicsRetriever
                .getColorSceneMode(FRONT_CAMERA);
        addProbeHeaderToUi("FRONT CAMERA AVAILABLE SCENE MODES");
        if (availableEffectsBackCam.isEmpty()) {
            addProbeResultToUi("No modes found");
        } else {
            addProbeResultToUi(availableSceneModesFrontCamera);
        }
    }

    private void addProbeResultToUi(List<String> messages) {
        for (String message : messages) {
            addProbeResultToUi(message);
        }
    }

    private void addProbeResultToUi(String message) {
        probeLogs.append(" " +message +"\n");
    }

    private void addProbeHeaderToUi(String headerText) {
        probeLogs.append(
                String.format("\n%s\n---------------------\n", headerText));
    }
}
