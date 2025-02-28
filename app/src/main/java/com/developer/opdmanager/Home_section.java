package com.developer.opdmanager;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home_section extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_section, container, false);

        ImageView myImageView = view.findViewById(R.id.search_button);
        ImageView homeVisit = view.findViewById(R.id.homeVisit);

        // Popup elements
        RelativeLayout popupOverlay = view.findViewById(R.id.popupOverlay);
        LinearLayout popupBox = view.findViewById(R.id.popupBox);
        TextView symptomTitle = view.findViewById(R.id.symptomTitle);
        TextView nameTitle = view.findViewById(R.id.nameTitle);
        TextView symptomDescription = view.findViewById(R.id.symptomDescription);
        Button btnClosePopup = view.findViewById(R.id.btnClosePopup);

        Button btnFever = view.findViewById(R.id.btnFever);
        Button btnCough = view.findViewById(R.id.btnCough);
        Button btnSnuffle = view.findViewById(R.id.btnSnuffle);

        // Assign click listeners
        setPopupButton(view, btnFever, popupOverlay, popupBox, symptomTitle, symptomDescription,
                "Fever", "Fever is a temporary increase in body temperature, often due to an illness.");

        setPopupButton(view, btnCough, popupOverlay, popupBox, symptomTitle, symptomDescription,
                "Cough", "A cough is a reflex that helps clear the airways of mucus and irritants.");

        setPopupButton(view, btnSnuffle, popupOverlay, popupBox, symptomTitle, symptomDescription,
                "Snuffle", "A snuffle is a mild nasal congestion often caused by allergies or a cold.");

        // Close button listener
        btnClosePopup.setOnClickListener(v -> closePopup(popupOverlay, popupBox));

        // Navigation
        homeVisit.setOnClickListener(v -> startActivity(new Intent(getActivity(), Diverted.class)));
        myImageView.setOnClickListener(v -> startActivity(new Intent(getActivity(), search_doctor.class)));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            Log.d("doremon","pubg-" + userId);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    String name = documentSnapshot.getString("Name");
                                    if (name != null) {
                                        String[] nameParts = name.split(" ", 2);
                                        nameTitle.setText("Hello, " + nameParts[0]);
                                    }
                                }else {
                                    nameTitle.setText("No name found");
                                }
                    });
        }
        return view;
    }

    // Reusable function to show popup
    private void setPopupButton(View view, Button button, RelativeLayout popupOverlay, LinearLayout popupBox,
                                TextView symptomTitle, TextView symptomDescription,
                                String title, String description) {
        button.setOnClickListener(v -> {
            symptomTitle.setText(title);
            symptomDescription.setText(description);

            popupOverlay.setVisibility(View.VISIBLE);
            popupBox.setVisibility(View.VISIBLE);

            // Fade-in animation for background
            AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setDuration(300);
            popupOverlay.startAnimation(fadeIn);

            // Scale-in animation for popup
            ScaleAnimation scaleAnimation = new ScaleAnimation(
                    0.5f, 1.0f, 0.5f, 1.0f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(300);
            scaleAnimation.setFillAfter(true);
            popupBox.startAnimation(scaleAnimation);
        });
    }

    // Reusable function to close popup
    private void closePopup(RelativeLayout popupOverlay, LinearLayout popupBox) {
        // Fade-out animation for background
        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(300);
        popupOverlay.startAnimation(fadeOut);

        // Scale-out animation for popup
        ScaleAnimation scaleOut = new ScaleAnimation(
                1.0f, 0.8f, 1.0f, 0.8f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleOut.setDuration(200);
        popupBox.startAnimation(scaleOut);

        popupOverlay.postDelayed(() -> {
            popupOverlay.setVisibility(View.GONE);
            popupBox.setVisibility(View.GONE);
        }, 300);
    }
}
