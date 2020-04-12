// Class to create all the enemy 
public class enemy {
    private String enemyname;
    private int enemyhealth;
    private int enemydamage;

//A constructor with three parameter
public enemy (String name, int health, int damage ) {
    enemyname = name;
    enemyhealth = health;
    enemydamage = damage;
}
// All setters
public void setname(String name)
{
  enemyname = name;
}
public void sethealth(int health)
{
  enemyhealth = health;
}
public void setdamage(int damage)
{
  enemydamage = damage;
}
//All Getters
public String getname()
  {
    return enemyname;
  }
  public int gethealth()
  {
    return enemyhealth;
  }
  public int getdamage()
  {
    return enemydamage;
  }
}
