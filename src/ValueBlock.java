import java.util.ArrayList;


public class ValueBlock 
{
	int value;
	int debugID;
	boolean solved = false;
	ArrayList<Integer> possibleValues;
	public ValueBlock(int value, int debugID)
	{
		this.value = value;
		this.debugID = debugID;
		resetPossibilities();
	}
	public void resetPossibilities()
	{
		possibleValues = new ArrayList<Integer>();
		if (this.value != 0)
		{
			solved = true;
			possibleValues.add(value);
		}
		else
		{
			for (int i = 1; i <= 9; i++)
			{
				possibleValues.add(i);
			}
		}
	}
	public ArrayList<Integer> possibilities()
	{
		ArrayList<Integer>tempArray = new ArrayList<Integer>();
		for (int i = 0; i < possibleValues.size(); i++)
		{
			Integer tempInt = new Integer(possibleValues.get(i));
			tempArray.add(tempInt);
		}
		return tempArray;
	}
	public ValueBlock clone()
	{
		ValueBlock clone = new ValueBlock(value, debugID);
		clone.solved = solved;
		return clone;
	}
	public void exclude(ArrayList<Integer>exclusions)
	{
		if (!solved)
		{
			for (int j = 0; j < exclusions.size(); j++)
			{
				if (possibleValues.contains(exclusions.get(j)))
				{
					//possible error here with unwrapping, if it does funky stuff, check here
					possibleValues.remove(exclusions.get(j));
					j--;
				}
			}
			if (possibleValues.size() == 1)
			{
				solved = true;
				value = possibleValues.get(0);
			}
		}
	}
}
