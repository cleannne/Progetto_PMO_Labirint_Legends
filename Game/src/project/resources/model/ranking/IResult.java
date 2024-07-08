package project.resources.model.ranking;

public interface IResult {
    void setUser(final IUser user);

    void setCharacterChosen(final String characterChosen);

    void setTotalCoins(final int coins);

    void setTotalMatchDuration(final int time);

    String getResult();
}
