package de.akquinet.engineering.vaadin.exercises.grid;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import de.akquinet.engineering.vaadin.ComponentView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class EditableGridView implements View, ComponentView
{
    public static final String VIEW_NAME = "editablegrid";

    private final VerticalLayout rootLayout = new VerticalLayout();

    public EditableGridView()
    {
        final List<Player> playerList = PlayerGenerator.createPlayers(10);

        final Grid<Player> grid = new Grid<>(Player.class);
        grid.setItems(playerList);

        grid.setColumns("name", "dateOfBirth");

        final Grid.Column<Player, String> genderColumn = grid
                .addColumn(EditableGridView::getGenderColumnHtmlContent)
                .setRenderer(new HtmlRenderer())
                .setCaption("Sex");

        // TODO: make the grid rows editable
        // Tip: get the binder for the row's Player object with grid.getEditor().getBinder();

        // TODO: make the name column editable
        // Tips:
        // 1) create a text field for the name column
        // 2) use the binder to bind the text field with bind("name") to the 'name' property of the player
        // 3) make the text field required with asRequired(..)
        // 4) set the binding to the name column with setEditorBinding(..)

        // TODO: make the date column editable
        // Tip: set the date format of the date field to "yyyy-MM-dd"

        // TODO: make the gender column editable
        // Tips:
        // 1) create an instance of ComboBox<Gender>
        // 2) set the available gender items (Gender.values())
        // 3) set the ItemIconGenerator using the icon from the GenderPresentation class
        // 4) set the ItemCaptionGenerator and use the GenderPresentation class to get the gender name
        // 5) disable the empty selection with setEmptySelectionAllowed(..)
        // 6) bind the the combo box with the binder to the Player's gender getter and setter
        // 7) set the column's editor binding

        // TODO: make the grid editable
        // Tip: use setEnabled(..) on the grid's editor

        grid.setHeightByRows(10d);
        rootLayout.setSizeFull();
        rootLayout.addComponent(grid);

        // show the saved bean
        grid.getEditor().addSaveListener(event -> Notification.show(event.getBean().toString()));

        // show the underlying data
        final Label showValuesLabel = new Label();
        showValuesLabel.setContentMode(ContentMode.HTML);
        final Button showPlayersButton = new Button("show players",
                e -> showValuesLabel.setValue(playerList
                        .stream()
                        .map(p -> p.toString())
                        .collect(Collectors.joining("<br/>"))));
        rootLayout.addComponents(showPlayersButton, showValuesLabel);
        rootLayout.setExpandRatio(showValuesLabel, 1f);

    }

    private static String getGenderColumnHtmlContent(final Player player)
    {
        final GenderPresentation genderPresentation = GenderPresentation
                .getPresentation(player.getGender());
        return String.format("%s %s",
                             genderPresentation
                                     .getIcon().getHtml(),
                             genderPresentation.getName());
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
