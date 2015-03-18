package org.gamerex.btl2.states;

public interface ActionResolver {
	public void showInterstital();
	public void setTrackerScreenName(String path);
	public String getStringResourceByName(String aString);
	public void bill(String arg);
	public void SaveSettings(String name,String Value);
	public int getIntSettings(String name);
	public boolean getBoolSettings(String name);
	public boolean getAds();
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(int score);
	public void unlockAchievementGPGS(String achievementId);
	public void getLeaderboardGPGS();
	public void getAchievementsGPGS();

}
