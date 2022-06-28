package Shopping.Controllers;

import Shopping.Item;
import Shopping.Main;
import Shopping.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(item);
    }

    private Item item;
    private MyListener myListener;

    public void setData(Item item, MyListener myListener) {
        this.item = item;
        this.myListener = myListener;
        nameLabel.setText(item.getName());
        priceLable.setText(Main.CURRENCY + item.getPriceString());
        Image image = new Image(getClass().getResourceAsStream(item.getImgSrc()));
        img.setImage(image);
    }
}
