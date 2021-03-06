package de.akquinet.engineering.vaadin.exercises.events;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import de.akquinet.engineering.vaadin.ComponentView;
import de.akquinet.engineering.vaadin.timerextension.TimerExtension;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class EventsView implements ComponentView, View
{
    // the start view needs to have the empty string as view name
    public static final String VIEW_NAME = "";

    private final VerticalLayout rootLayout = new VerticalLayout();

    private int secondsOnPage = 0;
    private TimerExtension timerExtension = null;

    public EventsView()
    {
        final Label nameLabel = new Label();

        final TextField nameField = new TextField("Name");
        nameField.addValueChangeListener(e -> nameLabel
                .setValue("You are " + nameField.getValue() + "."));

        // BLUR gives the old Vaadin 7 behavior, LAZY is default.
        nameField.setValueChangeMode(ValueChangeMode.BLUR);

        final Button showNotificationButton = new Button("Say Hello");
        showNotificationButton.addClickListener((Button.ClickListener) e -> Notification
                .show("Hello " + nameField.getValue() + "!"));
        rootLayout.addComponents(nameField, nameLabel, showNotificationButton);

        // bonus: show the seconds on page by counting the timer event that occurs every second
        final String counterLabelText = "seconds on page: ";
        final Label counterLabel = new Label(counterLabelText + secondsOnPage);
        timerExtension = TimerExtension.create(counterLabel);
        timerExtension.setIntervalInMs(1000);
        timerExtension.addTimerListener(e -> counterLabel.setValue(counterLabelText + ++secondsOnPage));
        rootLayout.addComponent(counterLabel);
    }

    @Override
    public Component getComponent()
    {
        return rootLayout;
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event)
    {
        timerExtension.start();
    }
}
