package de.akquinet.engineering.vaadin.exercises.grid;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.SerializableComparator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.StyleGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import de.akquinet.engineering.vaadin.ComponentView;

import java.text.NumberFormat;
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

        final StyleGenerator<Player> alignRightStyle = player -> "align-right";

        // Bonus: show the rank of each player
        playerGrid.addColumn(player -> playerList.indexOf(player) + 1)
                .setStyleGenerator(alignRightStyle)
                .setCaption("Rank");

        // show the player's name
        final Grid.Column<Player, String> nameColumn = playerGrid
                .addColumn(Player::getName)
                .setCaption("Name");

        // show the player's date of birth
        playerGrid.addColumn(player -> player.getDateOfBirth().format(dateTimeFormatter))
                .setCaption("Date of Birth")
                .setComparator((SerializableComparator<Player>) (o1, o2) -> o1.getDateOfBirth()
                        .compareTo(o2.getDateOfBirth()))
                .setWidth(200.0d)
                .setResizable(false)
                .setHidable(true);

        // show the player's age
        playerGrid.addColumn(Player::getAge)
                .setCaption("Age")
                .setHidable(true);

        // Bonus: show the player's sex with symbols in different colors
        playerGrid.addColumn(player -> GenderPresentation
                .getPresentation(player.getGender()).getIcon().getHtml())
                .setRenderer(new HtmlRenderer())
                .setStyleGenerator(player -> GenderPresentation
                        .getPresentation(player.getGender()).getStyleName())
                .setComparator((SerializableComparator<Player>) (o1, o2) -> Gender.compare(o1.getGender(), o2.getGender()))
                .setCaption("Sex").setHidable(true);

        // show the player's points
        playerGrid
                .addColumn(Player::getPoints)
                .setRenderer(new NumberRenderer(NumberFormat.getNumberInstance(UI.getCurrent().getLocale())))
                .setStyleGenerator(alignRightStyle)
                .setCaption("Points");
        // show the player's medals
        playerGrid
                .addColumn(Player::getMedals)
                .setStyleGenerator(alignRightStyle)
                .setCaption("Medals");

        // filtering by player's name
        {
            final HeaderRow filterRow = playerGrid.appendHeaderRow();
            final TextField nameFilterTextField = new TextField();
            nameFilterTextField.setPlaceholder("Filter");
            filterRow.getCell(nameColumn).setComponent(nameFilterTextField);
            nameFilterTextField.addValueChangeListener(event -> dataProvider
                    .setFilter(Player::getName,
                               s -> s.toLowerCase(UI.getCurrent().getLocale())
                                       .contains(event.getValue()
                                               .toLowerCase(UI.getCurrent().getLocale()))));
        }

        // layout grid
        playerGrid.setFrozenColumnCount(1);
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
