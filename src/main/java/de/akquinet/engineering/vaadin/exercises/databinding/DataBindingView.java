package de.akquinet.engineering.vaadin.exercises.databinding;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.engineering.vaadin.ComponentView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class DataBindingView implements View, ComponentView
{
    public static final String VIEW_NAME = "dataBinding";

    private final VerticalLayout rootLayout = new VerticalLayout();

    public DataBindingView()
    {
        // create dummy data
        final List<Locale> localeList = Arrays
                .asList(Locale.GERMANY, Locale.US, Locale.FRANCE, new Locale("fi", "FI"));

        final List<Genre> genreList = Arrays
                .asList(new Genre("non-fiction", "documentation"),
                        new Genre("fiction", "action"),
                        new Genre("fiction", "adventure"),
                        new Genre("fiction", "comedy"),
                        new Genre("fiction", "drama"),
                        new Genre("fiction", "science fiction"));

        final Movie movie = Movie.create("Trumbo", 124, new HashSet<>(Arrays
                .asList(Locale.GERMANY, Locale.US)),
                                         new Genre("fiction", "drama"));

        // create the fields
        final TextField titleField = new TextField("Title");
        final TextField durationField = new TextField("Duration");
        final ComboBox<Genre> genreSelect = new ComboBox<>("Genre");
        genreSelect.setItemCaptionGenerator(Genre::getSubGenre);
        genreSelect.setItems(genreList);

        final TwinColSelect<Locale> localeSelect = new TwinColSelect<>("Languages");
        localeSelect.setItems(localeList);
        localeSelect.setItemCaptionGenerator(locale -> locale
                .getDisplayLanguage(UI.getCurrent().getLocale()));

        // do the binding
        final Binder<Movie> binder = new Binder<>();
        binder.forField(titleField)
                .asRequired("The movie must have a title")
                .bind(Movie::getTitle, Movie::setTitle);
        binder.forField(durationField)
                .withConverter(new StringToIntegerConverter("This is not a number"))
                .withValidator(duration -> duration > 0, "The duration must be greater zero")
                .bind(Movie::getDurationInMinutes, Movie::setDurationInMinutes);
        binder.bind(genreSelect, Movie::getGenre, Movie::setGenre);
        binder.bind(localeSelect, Movie::getLocales, Movie::setLocales);

        binder.readBean(movie);

        final FormLayout formLayout = new FormLayout(titleField, durationField, genreSelect, localeSelect);

        final Button saveButton = new Button("save", event ->
        {
            try
            {
                binder.writeBean(movie);
            }
            catch (final ValidationException e)
            {
                Notification.show("invalid data", Notification.Type.ERROR_MESSAGE);
            }
        });
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        final Button cancelButton = new Button("cancel", event -> binder.readBean(movie));

        final HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);

        rootLayout.addComponents(formLayout, buttonLayout);

        final Button showCurrentValue = new Button("show movie values",
                event -> Notification.show(movie.toString()));
        rootLayout.addComponent(showCurrentValue);
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {

    }

    @Override
    public Component getComponent()
    {
        return rootLayout;
    }
}
