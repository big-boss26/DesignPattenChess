package User;

import Game.GamePlayer;

import java.util.EventObject;

public class LoginEvent extends EventObject
{
    private GamePlayer[] twoPlayers=new GamePlayer[2];
    public LoginEvent(Object source, GamePlayer[] twoplayers)
    {
        super(source);
        this.twoPlayers=twoplayers.clone();
        //this.twoPlayers[1]=twoplayers[1];
        System.out.println("xxx");
    }

    public GamePlayer[] getTwoPlayers() {
        return twoPlayers;
    }
}