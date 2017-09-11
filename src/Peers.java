import java.util.ArrayList;


public class Peers 
{
	ArrayList<ValueBlock> blocks;
	ArrayList<ArrayList<Integer>> partitions;
	int sum;
	public Peers(int sum)
	{
		this.sum = sum;
		blocks = new ArrayList<ValueBlock>();
		partitions = new ArrayList<ArrayList<Integer>>();
	}
	public Peers clone()
	{
		Peers tempPeer = new Peers(sum);
		tempPeer.blocks = blocks;
		tempPeer.partitions = clonePartitions();
		return tempPeer;
	}
	public ArrayList<ArrayList<Integer>> clonePartitions()
	{
		ArrayList<ArrayList<Integer>>tempArray = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < partitions.size(); i++)
		{
			ArrayList<Integer>possible = new ArrayList<Integer>();
			for (int j = 0; j < partitions.get(i).size(); j++)
			{
				possible.add(partitions.get(i).get(j));
			}
			tempArray.add(possible);
		}
		return tempArray;
	}
	public void addPeer(ValueBlock block)
	{
		blocks.add(block);
	}
	public void initializePartitions()
	{
		partitions = getPartitions(this.sum, this.blocks.size(),1,9);
		printPartitions();
	}
	public void updatePartitions()
	{
		/*for (int i = 0; i < blocks.size(); i++)
		{
			ValueBlock tempBlock = blocks.get(i);
			if (tempBlock.solved)
			{
				for (int j = 0; j < partitions.size(); j++)
				{
					if (!partitions.get(j).contains(tempBlock.value))
					{
						partitions.remove(j);
						j--;
					}
				}
			}
		}*/
		for (int i = 0; i < partitions.size(); i++)
		{
			for (int j = 0; j < blocks.size(); j++)
			{
				if (blocks.get(j).solved)
				{
					if (!partitions.get(i).contains(blocks.get(j).value))
					{
						partitions.remove(i);
						i--;
						break;
					}
				}
			}
		}
		/*System.out.println(blocks.size() + " blocks with a sum of " + sum);
		printSolvedBlocks();
		printPartitions();*/
	}
	public void exclusions()
	{	
		ArrayList<Integer>include = new ArrayList<Integer>();
		for (int i = 0; i < partitions.size(); i++)
		{
			ArrayList<Integer>option = partitions.get(i);
			for (int j = 0; j < option.size(); j++)
			{
				if (!include.contains(option.get(j)))
				{
					include.add(option.get(j));
				}
			}
		}
		ArrayList<Integer>exclude = new ArrayList<Integer>();
		for (int i = 1; i <= 9; i++)
		{
			if (!include.contains(i))
			{
				exclude.add(i);
			}
		}
		for (int i = 0; i < blocks.size(); i++)
		{
			ValueBlock temp = blocks.get(i);
			if (temp.solved)
			{
				if (!exclude.contains(temp.value))
				{
					exclude.add(temp.value);
				}
			}
		}
		for (int i = 0; i < blocks.size(); i++)
		{
			blocks.get(i).exclude(exclude);
		}
	}
	public void printSolvedBlocks()
	{
		System.out.print("Solved Blocks: [");
		for (int i = 0; i < blocks.size(); i++)
		{
			if (blocks.get(i).solved)
			{
				System.out.print(blocks.get(i).value);
			}
			if (i != blocks.size()-1)
			{
				System.out.print(",");
			}
		}
		System.out.println("]");
	}
	public void printPartitions()
	{
		System.out.println("Partitions For Sum of " + sum + " with " + blocks.size() + " blocks");
		for (int i = 0; i < partitions.size(); i++)
		{
			ArrayList<Integer>tempArray = partitions.get(i);
			printOption(tempArray);
		}
	}
	public void printOption(ArrayList<Integer>option)
	{
		System.out.print("[");
		for (int j = 0; j < option.size(); j++)
		{
			System.out.print(option.get(j));
			if (j != option.size()-1)
			{
				System.out.print(", ");
			}
		}
		System.out.println("]");
	}
	public ArrayList<ArrayList<Integer>> getPartitions(int sum, int numOfIntegers, int minValue, int maxValue)
	{
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		
		if (sum <= 1 || numOfIntegers == 1)
		{
			if (sum >= minValue && sum <= maxValue)
			{
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(sum);
				result.add(temp);
			}
			return result;
		}
		
		for (int y = Math.min(sum, maxValue); y >= minValue; y--)
	    {
			ArrayList<ArrayList<Integer>> recursivePartitions = getPartitions(sum - y, numOfIntegers - 1, minValue, y);
			for (int i = 0; i < recursivePartitions.size(); i++)
			{
				recursivePartitions.get(i).add(y);
				/* Don't add partitions that have duplicate values
	           	or don't fit within numberOfIntegers. */
				int count = recursivePartitions.get(i).size();
				if ((count == numOfIntegers) && distinct(recursivePartitions.get(i)))
				{
					result.add(recursivePartitions.get(i));
				}
	        }
	     }
		return result;
	}
	public boolean distinct(ArrayList<Integer>tempList)
	{
		for (int i = 1; i <= 9; i++)
		{
			int first = tempList.indexOf(i);
			int last = tempList.lastIndexOf(i);
			if (first != last)
			{
				return false;
			}
		}
		return true;
	}
	
}
