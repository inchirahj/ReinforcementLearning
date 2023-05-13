import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

class Main
{

  private static int numSticks = 10;
	private static int [][] qValues = new int[3][10];
	private static int [] moves = new int[10];
	private static boolean game = true;
	private static int player;

  public static void main(String[] args) throws FileNotFoundException 
  {
		System.out.println("Let's start playing!");

    
    File file = new File("QLearning.txt");

		if(file.exists())
    {
			Scanner fileReader = new Scanner(file);
			for(int i = 0; i < 3; i++)
        {
          for(int j = 0; j < 10; j++)
            {
              qValues[i][j] = fileReader.nextInt();
            }
        }
			fileReader.close();
		}

    else
    {
			for(int i = 0; i < 3; i++)
        {
          for(int j = 0; j < 10; j++)
            {
              qValues[i][j] = 0;
            }
        }
      
      qValues[0][0] = -1;
      qValues[0][1] = -1;
			qValues[0][2] = 1;
			qValues[1][0] = -1;
      qValues[1][1] = 1;
      qValues[1][2] = -1;
			qValues[2][0] = 1;
      qValues[2][1] = -1;
      qValues[2][2] = -1;
		}

    for(int i = 0; i < 10; i++)
    {
      moves[i] = 0;
    }

    Scanner in = new Scanner(System.in);
    
    while(game == true)
    {
      player = 1;
      System.out.println("It's the computer's turn.");
      int computerSticks = nextMove();
      numSticks = numSticks - computerSticks;
			System.out.println("The computer took " + computerSticks + " sticks.");
			game = gameOver();
			System.out.println("There are " + numSticks + " sticks left.");

      if(game == false)
      {
        break;
      }

      System.out.println("It's your turn. Enter the number of sticks you want to take:");
      
      player = 2;
			int playerSticks = in.nextInt();
			while(playerSticks > 3 || playerSticks <= 0)
      {
        System.out.println("Invalid input. Please choose a number between 1 and 3");
        playerSticks = in.nextInt();
			}
        
      while(playerSticks > numSticks)
      {
				System.out.println("Invalid input. Please choose a number between 1 and" + numSticks);
				playerSticks = in.nextInt();
			}

      numSticks = numSticks - playerSticks;
			System.out.println("You took " + playerSticks + " sticks.");
      
			game = gameOver();
      
			System.out.println("There are " + numSticks + " sticks left.");
		}

    if(player == 1)
    {
      System.out.println("The computer won!");
    }
		else
    {
      System.out.println("You won!");
    }
      
		qLearning();
    in.close();

	}
    

  public static boolean gameOver()
  {
		if(numSticks == 0)
    {
      return false;
    }
		return true;
	}

  public static int nextMove()
  {
		int move = 1;
		moves[numSticks - 1] = 1;
    
		if(qValues[1][numSticks - 1] > qValues[2][numSticks - 1])
    {
			move = 2;
			moves[numSticks - 1] = 2;
		}
    
		else if(qValues[0][numSticks - 1] > qValues[2][numSticks - 1] 
				&& qValues[0][numSticks - 1] > qValues[1][numSticks - 1])
    {
      move = 3;
			moves[numSticks - 1] = 3;
    } 
    
		return move;
	}

  public static void qLearning()
  {
		if(player == 1)
    {
			for(int i = 0; i < 10; i ++)
      {
				if(moves[i] != 0)
        {
					qValues[3 - moves[i]][i]++;
					moves[i] = 0;
				}
			}
		}
      
		else
    {
			for(int i = 0; i < 10; i ++)
      {
				if(moves[i] != 0)
        {
					qValues[3 - moves[i]][i]--;
					moves[i] = 0;
				}
			}
		}
    
    try 
    {
      File QLearning = new File("QLearning.txt");
      FileOutputStream fout = new FileOutputStream(QLearning);
      OutputStreamWriter output = new OutputStreamWriter(fout);    
      Writer out = new BufferedWriter(output);
      for(int i = 0; i < 3; i++)
      {
    		for(int j = 0; j < 10; j++)
        {
    			out.write(qValues[i][j] + " ");
    		}
    	  out.write("\n");
      }

      out.close();
    } 

    catch (IOException e) 
    {
      
    }
  }
   
}