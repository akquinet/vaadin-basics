package de.akquinet.engineering.vaadin.exercises.face;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.engineering.vaadin.ComponentView;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class FaceView implements ComponentView, View
{
    public static final String VIEW_NAME = "face";

    private final VerticalLayout rootLayout = new VerticalLayout();

    public FaceView()
    {
        final Button leftEyeButton = new Button(VaadinIcons.EYE);
        leftEyeButton.setStyleName(ValoTheme.BUTTON_LINK);
        leftEyeButton.addStyleName("eye");
        leftEyeButton.setDescription("left eye");

        final Button rightEyeButton = new Button(VaadinIcons.EYE);
        rightEyeButton.setStyleName(ValoTheme.BUTTON_LINK);
        rightEyeButton.addStyleName("eye");
        rightEyeButton.setDescription("right eye");

        final HorizontalLayout horizontalLayout = new HorizontalLayout(leftEyeButton, rightEyeButton);

        final Button noseButton = new Button(VaadinIcons.CARET_UP);
        noseButton.setStyleName(ValoTheme.BUTTON_LINK);
        noseButton.addStyleName("nose");
        noseButton.setDescription("nose");

        final TextField mouthField = new TextField();
        mouthField.setWidth("200px");
        mouthField.setValue("|||||||||||||||||||||");
        mouthField.setDescription("mouth");

        final VerticalLayout faceLayout = new VerticalLayout();
        faceLayout.setStyleName("face");
        faceLayout.addComponents(horizontalLayout, noseButton, mouthField);

        // layout adjustments
        horizontalLayout.setComponentAlignment(leftEyeButton, Alignment.MIDDLE_CENTER);
        horizontalLayout.setComponentAlignment(rightEyeButton, Alignment.MIDDLE_CENTER);
        horizontalLayout.setWidth("100%");

        faceLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
        faceLayout.setComponentAlignment(noseButton, Alignment.MIDDLE_CENTER);
        faceLayout.setExpandRatio(noseButton, 1.0f);
        faceLayout.setComponentAlignment(mouthField, Alignment.MIDDLE_CENTER);

        faceLayout.setWidth("350px");
        faceLayout.setHeight("400px");

        rootLayout.addComponent(faceLayout);
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event)
    {

    }

    @Override
    public Component getComponent()
    {
        return rootLayout;
    }
}
