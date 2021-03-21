package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.kotcrab.vis.ui.widget.VisTextButton;
import main.primary.Global;
import main.primary.gameplay.Player;
import main.primary.gameplay.Skill;
import main.primary.gameplay.Trader;

import java.util.Random;

public class TraderScene extends Scene {

    private Trader trader;
    private Label tradeDesc;
    private Label resultLabel; // Result of the interaction with the trader

    private Table traderOptions;
    private VisTextButton buyBtn;
    private VisTextButton negotiateBtn;

    public void create() {
        // Trader Encounter title
        table.align(Align.top);
        table.add(title("Trader Encounter!", Color.YELLOW)).pad(150f);
        table.row();

        this.trader = new Trader();
        this.tradeDesc = label(String.format("%d %s for %d (%d each)",
                trader.getItemCount(),
                trader.getItem().getName(),
                trader.getTotalPrice(),
                trader.getPricePerItem()),
                Color.YELLOW, 0.7f);
        table.add(tradeDesc);
        table.row();

        table.add(label(trader.getItem().getDescription(), Color.YELLOW, 0.7f));
        table.row();

        this.resultLabel = label("", Color.WHITE, 0.7f);
        table.add(this.resultLabel);

        // Table containing trader button options
        this.traderOptions = new Table();
        resize((int) table.getWidth(), (int) table.getHeight());
        traderOptions.align(Align.bottom);

        final int SPACING = 80;

        this.buyBtn = (VisTextButton) buyButton();
        traderOptions.add(buyBtn).padRight(SPACING);

        traderOptions.add(textButton("Ignore", Color.GREEN, () ->
                travelToDestination("Ignored Trader.\n"
                        + "Traveling to destination")
        )).padRight(SPACING);

        traderOptions.add(robButton()).padRight(SPACING);

        this.negotiateBtn = (VisTextButton) negotiateButton();
        traderOptions.add(negotiateBtn);

        traderOptions.bottom().pad(30);
        sceneLoader.addActor(traderOptions);
    }

    protected void resize(int width, int height) {
        super.resize(width, height);
        if (traderOptions != null) {
            traderOptions.setPosition(sceneLoader.xOffset, sceneLoader.yOffset);
            traderOptions.setSize(table.getWidth(), table.getHeight());
        }
    }

    private Button buyButton() {
        Player player = Global.app.player;
        boolean canBuy = player.credits >= trader.getTotalPrice() && player.getShip().getCargo() >= 1;
        return textButton("Buy", canBuy ? Color.GREEN : Color.RED, () -> {
            if (player.credits >= trader.getTotalPrice() && player.getShip().getCargo() >= 1) {
                // only take as many as you can before the ship gets full
                int bought = 0;
                while (bought < trader.getItemCount()) {
                    player.getShip().addItem(trader.getItem());
                    player.credits -= trader.getPricePerItem();
                    bought++;
                }
                travelToDestination("Successfully Bought " + bought + " items\n"
                        + "for " + bought * trader.getPricePerItem() + " credits.\n"
                        + "Traveling to destination.");
            }
        });
    }

    private Button robButton() {
        Player player = Global.app.player;
        return textButton("Rob", Color.GREEN, () -> {
            Random rand = Global.app.rand;
            if (player.rollSkillCheck(Skill.SkillType.FIGHTER)) {
                int numRobbed = rand.nextInt(trader.getItemCount()) + 1;
                int num = 0;
                while (num < numRobbed && player.getShip().hasCargoSpace()) {
                    player.getShip().addItem(trader.getItem());
                    num++;
                }
                travelToDestination("Robbed Trader Successfully!\n"
                        + "Received " + num + " items\n"
                        + "Traveling to destination.");
            } else {
                int damage = rand.nextInt(5);
                player.getShip().takeDamage(damage);
                travelToDestination("Failed to Rob Trader.\n"
                        + "Ship Lost " + damage + " HP\n"
                        + "Traveling to destination.");
            }
        });
    }

    private Button negotiateButton() {
        Player player = Global.app.player;
        return textButton("Negotiate", Color.GREEN, () -> {
            if (trader.canNegotiate()) {
                boolean tradeResult = player.rollSkillCheck(Skill.SkillType.MERCHANT);
                trader.negotiate(tradeResult);
                if (tradeResult) {
                    resultLabel.setText("Negotiation Succeeded: Price reduced!");
                } else {
                    resultLabel.setText("Negotiation Failed: Price increased!");
                }
                traderOptions.setVisible(false);
                Timer.schedule(new Timer.Task() {
                    public void run() {
                        resultLabel.setText("");
                        traderOptions.setVisible(true);
                    }
                }, 4);

                tradeDesc.setText(String.format("%d %s for %d (%d each)",
                        trader.getItemCount(),
                        trader.getItem().getName(),
                        trader.getTotalPrice(),
                        trader.getPricePerItem()));

                if (player.credits >= trader.getPricePerItem() && player.getShip().getCargo() >= 1) {
                    buyBtn.getStyle().fontColor = Color.GREEN;
                } else {
                    buyBtn.getStyle().fontColor = Color.RED;
                }

                negotiateBtn.getStyle().fontColor = Color.RED;
            }
        });
    }

    private void travelToDestination(String resultStr) {
        traderOptions.remove(); // Disable other buttons from being clicked
        resultLabel.setText(resultStr);
        Timer.schedule(new Timer.Task() {
            public void run() {
                sceneLoader.setScene(new MapScene());
            }
        }, 4);

        // TODO handle lose scene
    }
}
