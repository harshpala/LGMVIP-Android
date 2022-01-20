package com.example.lgmvip_augmentedfaces;

import android.os.Bundle;
import android.util.Pair;

import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.AugmentedFace;
import com.google.ar.core.Frame;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ModelRenderable modelRenderable;
    private Texture texture;
    private boolean isAdded = false;
    private final HashMap<AugmentedFace, AugmentedFaceNode> faceNodeMap = new HashMap<>();
    private AugmentedFaceNode augmentedFaceNode;
    private final ArrayList<Pair<Integer,Integer>> Filters = new ArrayList<>(Arrays.asList(
            new Pair<>(R.raw.fox_face, R.drawable.fox_face_mesh_texture),
            new Pair<>(R.raw.glasses1, R.drawable.moustache),
            new Pair<>(R.raw.glasses2, R.drawable.pwise),
            new Pair<>(R.raw.glasses3, R.drawable.redlips)));
    private int FilterIds=0;
    private boolean  changeModel = false;

    private void SetFilter(int ModelResource, int TextureResource)
    {
        ModelRenderable.builder()
                .setSource(this,ModelResource)
                .build()
                .thenAccept(modelRenderable -> {
                    this.modelRenderable = modelRenderable;
                    this.modelRenderable.setShadowCaster(false);
                    this.modelRenderable.setShadowReceiver(false);

                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "error loading model", Toast.LENGTH_SHORT).show();
                    return null;
                });
        Texture.builder()
                .setSource(this, TextureResource)
                .build()
                .thenAccept(textureModel -> texture = textureModel)
                .exceptionally(throwable -> {Toast.makeText(this, "Error loading texture", Toast.LENGTH_SHORT).show();return null; }
                );
    }
    private void ApplyFilter(int ModelResource, int TextureResource)
    {

        CustomArFragment customArFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        SetFilter(ModelResource,TextureResource);
        assert customArFragment != null;
        customArFragment.getArSceneView().setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        customArFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            if (modelRenderable == null || texture == null) {
                return;
            }
            Frame frame = customArFragment.getArSceneView().getArFrame();
            assert frame != null;
            Collection<AugmentedFace> augmentedFaces = frame.getUpdatedTrackables(AugmentedFace.class);

            for (AugmentedFace augmentedFace : augmentedFaces) {
                if (!faceNodeMap.containsKey(augmentedFace)) {

                    augmentedFaceNode = new AugmentedFaceNode(augmentedFace);
                    augmentedFaceNode.setParent(customArFragment.getArSceneView().getScene());
                    augmentedFaceNode.setFaceRegionsRenderable(modelRenderable);
                    augmentedFaceNode.setFaceMeshTexture(texture);
                    faceNodeMap.clear();
                    faceNodeMap.put(augmentedFace, augmentedFaceNode);



                    Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iterator = faceNodeMap.entrySet().iterator();
                    Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iterator.next();
                    AugmentedFace face = entry.getKey();
                    while (face.getTrackingState() == TrackingState.STOPPED) {
                        AugmentedFaceNode node = entry.getValue();
                        node.setParent(null);
                        iterator.remove();
                    }
                }
                else if(changeModel)
                {
                    faceNodeMap.get(augmentedFace).setFaceMeshTexture(texture);
                    faceNodeMap.get(augmentedFace).setFaceRegionsRenderable(modelRenderable);
                }
            }
            changeModel=true;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            FilterIds=(int)extras.getInt("CurrID");
        setContentView(R.layout.activity_main);
        int init =0;
        Pair<Integer, Integer> filter=Filters.get(init);
        ApplyFilter(filter.first,filter.second);

        SeekBar seekBar = findViewById(R.id.seekBar5);
        seekBar
                .setOnSeekBarChangeListener(
                        new SeekBar
                                .OnSeekBarChangeListener() {

                            // When the progress value has changed
                            @Override
                            public void onProgressChanged(
                                    SeekBar seekBar,
                                    int progress,
                                    boolean fromUser)
                            {


                                Pair<Integer, Integer> filter=Filters.get(progress);
                                ApplyFilter(filter.first,filter.second);
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar)
                            {

                                // This method will automatically
                                // called when the user touches the SeekBar
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar)
                            {

                                // This method will automatically
                                // called when the user
                                // stops touching the SeekBar
                            }
                        });


    }






}
