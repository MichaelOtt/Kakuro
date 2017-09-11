
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class KakuroGame
{
	public static boolean[][][] ruleArray;
	public static ValueBlock[][] valueArray;
	public static SumBlock[][] sumArray;
	public static ArrayList<Peers> peersArray = new ArrayList<Peers>();
	public static void main(String [] args)
	{
		initializeArrays();
		for (int i = 0; i < peersArray.size(); i++)
		{
			peersArray.get(i).initializePartitions();
		}
		ArrayList<ValueBlock> vArray = new ArrayList<ValueBlock>();
		vArray = arrayToList();
		solve(vArray,1);
		printPuzzle();
		for (int i = 0; i < peersArray.size(); i++)
		{
			peersArray.get(i).printSolvedBlocks();
			int calculated = 0;
			for (int j = 0; j < peersArray.get(i).blocks.size(); j++)
			{
				calculated += peersArray.get(i).blocks.get(j).value;
			}
			System.out.println("Calculated Sum: " + calculated);
			System.out.println("Sum: " + peersArray.get(i).sum);
			if (calculated != peersArray.get(i).sum)
			{
				System.out.println("SOMETHING WENT WRONG HERE --------------------------------------");
			}
		}
	}
	public static ArrayList<ValueBlock> arrayToList()
	{
		ArrayList<ValueBlock> vArray = new ArrayList<ValueBlock>();
		for (int r = 0; r < valueArray.length; r++)
		{
			for (int c = 0; c < valueArray[0].length; c++)
			{
				if (valueArray[r][c] == null)
				{
				}
				else
				{
					vArray.add(valueArray[r][c]);
				}
			}
		}
		return vArray;
	}
	public static void printPuzzle()
	{
		for (int r = 0; r < valueArray.length; r++)
		{
			System.out.print("[");
			for (int c = 0; c < valueArray[0].length; c++)
			{
				if (valueArray[r][c] == null)
				{
					System.out.print("S");
				}
				else
				{
					System.out.print(valueArray[r][c].value);
				}
				if (c != valueArray[0].length-1)
				{
					System.out.print(" ");
				}
			}
			System.out.println("]");
		}
		System.out.println();
	}
	public static boolean solve(ArrayList<ValueBlock> vArray, int level)
	{
		ArrayList<ValueBlock>previous;
		ArrayList<ValueBlock>fixArray;
		ArrayList<Peers>fixPeers;
		while (!solved(vArray))
		{
			previous = new ArrayList<ValueBlock>();
			fixArray = new ArrayList<ValueBlock>();
			fixPeers = new ArrayList<Peers>();
			for (int i = 0; i < vArray.size(); i++)
			{
				previous.add(vArray.get(i).clone());
			}
			//System.out.println("exclude at level " + level);
			simpleExclude();
			for (int i = 0; i < vArray.size(); i++)
			{
				if (vArray.get(i).possibleValues.size() == 0)
				{
					System.out.println("Failed on block at level " + level + " with ID: " + vArray.get(i).debugID);
					return false;
				}
			}
			for (int i = 0; i < peersArray.size(); i++)
			{
				if (peersArray.get(i).partitions.size() == 0)
				{
					System.out.println("Failed on peer at level " + level);
					return false;
				}
			}
			if (unchanged(previous,vArray))
			{
				for (int i = 0; i < vArray.size(); i++)
				{
					fixArray.add(vArray.get(i).clone());
				}
				for (int i = 0; i < peersArray.size(); i++)
				{
					fixPeers.add(peersArray.get(i).clone());
				}
				ValueBlock tempBlock = getLowestPossible(vArray);
				ArrayList<Integer>possibilities = tempBlock.possibilities();
				for (int i = 0; i < possibilities.size(); i++)
				{
					tempBlock.solved = true;
					tempBlock.value = possibilities.get(i);
					System.out.println("Going into level " + (level+1));
					if(solve(vArray,level+1))
					{
						System.out.println("success at level " + level);
						return true;
					}
					fixArray(fixArray,vArray);
					fixPeers(fixPeers,peersArray);
				}
				/*for (int i = 0; i < vArray.size(); i++)
				{
					if (!vArray.get(i).solved)
					{
						//System.out.println("trying next block at level " + level);
						ArrayList<Integer>possibilities = vArray.get(i).possibilities();
						for (int j = 0; j < possibilities.size(); j++)
						{
							//System.out.println("attempting " + possibilities.get(j) + " with block " + vArray.get(i).debugID);
							//System.out.println("trying next possibility at level " + level);
							vArray.get(i).solved = true;
							vArray.get(i).value = possibilities.get(j);
							if(solve(vArray,level+1))
							{
								System.out.println("success at level " + level);
								return true;
							}
							fixArray(fixArray,vArray);
							fixPeers(fixPeers,peersArray);
						}
					}
				}*/
				System.out.println("Going back to level " + level);
				return false;
			}
		}
		return true;
	}
	public static ValueBlock getLowestPossible(ArrayList<ValueBlock>blocks)
	{
		int lowest = 10;
		ValueBlock tempBlock = null;
		for (int i = 0; i < blocks.size(); i++)
		{
			if (!blocks.get(i).solved && blocks.get(i).possibleValues.size()<lowest)
			{
				lowest = blocks.get(i).possibleValues.size();
				tempBlock = blocks.get(i);
			}
		}
		return tempBlock;
	}
	public static void fixArray(ArrayList<ValueBlock> previous, ArrayList<ValueBlock>broken)
	{
		for (int i = 0; i < broken.size(); i++)
		{
			broken.get(i).value = previous.get(i).value;
			broken.get(i).solved = previous.get(i).solved;
			broken.get(i).resetPossibilities();
		}
	}
	public static void fixPeers(ArrayList<Peers> previous, ArrayList<Peers>broken)
	{
		for (int i = 0; i < broken.size(); i++)
		{
			broken.get(i).partitions = previous.get(i).clonePartitions();
		}
	}
	public static boolean unchanged(ArrayList<ValueBlock> previous, ArrayList<ValueBlock>current)
	{
		for (int i = 0; i < current.size(); i++)
		{
			if (previous.get(i).value != current.get(i).value)
			{
				return false;
			}
		}
		return true;
	}
	public static void simpleExclude()
	{
		for (int i = 0; i < peersArray.size(); i++)
		{
			peersArray.get(i).exclusions();
		}
		for (int i = 0; i < peersArray.size(); i++)
		{
			peersArray.get(i).updatePartitions();
		}
	}
	public static boolean solved(ArrayList<ValueBlock>vArray)
	{
		for (int i = 0; i < vArray.size(); i++)
		{
			if (!vArray.get(i).solved)
			{
				return false;
			}
		}
		return true;
	}
	public static void initializeArrays()
	{
		ArrayList<String[]>tempArray = new ArrayList<String[]>();
		
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File myfile = chooser.getSelectedFile();
			
			try 
			{
				tempArray = DataReader.splitLinesFromFile(myfile,"\\|");
			}
			catch (IOException e) 
	      	{
				e.printStackTrace();
	      	}
		}
		int height = tempArray.size();
		int width = tempArray.get(0).length;
		valueArray = new ValueBlock[height][width];
		sumArray = new SumBlock[height][width];
		for (int i = 0; i < height; i++)
		{
			DataReader.printArray(tempArray.get(i));
		}
		for (int row = 0; row < height; row++)
		{
			for (int col = 0; col < width; col++)
			{
				String block = tempArray.get(row)[col];
				try
				{
					int value = Integer.parseInt(block.trim());
					valueArray[row][col] = new ValueBlock(value,row*width+col);
				}
				catch(Exception e)
				{
					String[] sums = block.split("/");
					int rightSum = Integer.parseInt(sums[1].trim());
					int downSum = Integer.parseInt(sums[0].trim());
					SumBlock s = new SumBlock(rightSum,downSum);
					sumArray[row][col] = s;
				}
			}
		}
		for (int row = 0; row < height; row++)
		{
			for (int col = 0; col < width; col++)
			{
				SumBlock s = sumArray[row][col];
				if (s != null)
				{
					if (s.rightSum > 0)
					{
						int tempc = col+1;
						Peers tempPeers = new Peers(s.rightSum);
						while (tempc < width && valueArray[row][tempc] != null)
						{
							tempPeers.addPeer(valueArray[row][tempc]);
							tempc++;
						}
						peersArray.add(tempPeers);
					}
					if (s.downSum > 0)
					{
						int tempr = row+1;
						Peers tempPeers = new Peers(s.downSum);
						while (tempr < height && valueArray[tempr][col] != null)
						{
							tempPeers.addPeer(valueArray[tempr][col]);
							tempr++;
						}
						peersArray.add(tempPeers);
					}
				}
			}
		}
	}

}
