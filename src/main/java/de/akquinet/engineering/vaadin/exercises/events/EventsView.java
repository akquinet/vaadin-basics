package de.akquinet.engineering.vaadin.exercises.events;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
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

    // use this for the bonus task
    private int secondsOnPage = 0;
    private TimerExtension timerExtension = null;

    public EventsView()
    {
        final Label nameLabel = new Label();

        final TextField nameField = new TextField("Name");
        // TODO: add a value change listener to the nameField to update the label's value with the entered name

        // BLUR gives the old Vaadin 7 behavior, LAZY is default.
        nameField.setValueChangeMode(ValueChangeMode.BLUR);

        // TODO: add a button to show the name in a notification
        // Tips:
        // 1) create a button with a caption and a click listener
        // 2) in the click listener call Notification.show(..) to show the current value of the name field
        // 3) add the button to the layout

        rootLayout.addComponents(nameField, nameLabel);

        // Bonus: show the seconds on page by counting the timer event that occurs every second
        // Tips:
        // 1) create a label to show the counter
        // 2) create an instance of TimerExtension with TimerExtension.create(label)
        // 3) set the time interval with setIntervalInMs(..)
        // 4) add a TimerListener to the timer extension
        // 5) start the timer extension in enter(..)
    }

    @Override
    public Component getComponent()
    {
        return rootLayout;
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event)
    {
    }
}
