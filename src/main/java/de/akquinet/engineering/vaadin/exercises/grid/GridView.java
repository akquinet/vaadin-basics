package de.akquinet.engineering.vaadin.exercises.grid;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.StyleGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.akquinet.engineering.vaadin.ComponentView;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class GridView implements View, ComponentView
{
    public static final String VIEW_NAME = "grid";

    private final VerticalLayout rootLayout = new VerticalLayout();

    public GridView()
    {
        final List<Player> playerList = PlayerGenerator.createPlayers(1000);

        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("MMMM d, yyyy", UI.getCurrent().getLocale());

        final Grid<Player> playerGrid = new Grid<>();
        final ListDataProvider<Player> dataProvider = DataProvider
                .ofCollection(playerList);
        playerGrid.setDataProvider(dataProvider);

        // Use this style generator for the points and medals column
        final StyleGenerator<Player> alignRightStyle = player -> "align-right";

        // Bonus: show the rank of each player

        // TODO: show the player's name

        // TODO: show the player's date of birth and make it hidable
        // Tips:
        // 1) use the dateTimeFormatter in the ValueProvider
        // 2) set a comparator (SerializableComparator<Player>) and use the LocalDate's compareTo function,
        //      so that date are compared not by their string representation but by their values

        // TODO: show the player's age and make it hidable

        // Bonus: show the player's sex with symbols in different colors
        // Tips:
        // 1) use the GenderPresentation class to get the gender icons
        // 2) use an HtmlRenderer
        // 3) set a style generator and get the style from the GenderPresentation class
        // 4) set a comparator to make to column comparable, you can use the Gender's compare function

        // TODO: show the player's points (align right)
        // Tip: you can use the NumberRenderer (new NumberRenderer(NumberFormat.getNumberInstance(UI.getCurrent().getLocale()))) to get the points formatted

        // TODO: show the player's medals (align right)

        // TODO: enable filtering by player's name
        // Tips:
        // 1) create a new header row with appendHeaderRow()
        // 2) create a text field and set this component into the new row in the cell of the name
        // 3) add a value change listener to the text field
        // 4) in the listener set a filter on the dataProvider, use this interface:
        //      setFilter(ValueProvider<T, V> valueProvider, SerializablePredicate<V> valueFilter)
        //    and check whether or not the cell contains the filter string

        // layout grid
        // TODO: set the grid's frozen column count to 1
        playerGrid.setHeightByRows(10.0d);
        playerGrid.setWidth("100%");

        rootLayout.addComponent(playerGrid);
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
