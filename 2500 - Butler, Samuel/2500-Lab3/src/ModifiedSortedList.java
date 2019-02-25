
public class ModifiedSortedList<T extends Comparable<T>> extends ListSorted<T>
{
	public ModifiedSortedList()
	{
		super();
	}
	
	protected void find(T target)
	{
		// how it finds it is different the other list, because it is sorted that means we
		// can check to see if the target word comes before or after the word we are studying
		// therefore, once we hit a word that comes after the target that means we know its not
		// in the list and we need to add it. As well we keep an reference to the previous insertion
		// point. This is because if on average we insert in the middle of the list, we can check to
		// see if the word comes after that location, if it doesn't then we will start from the front
		// of the list, otherwise we can start from that stored location and only traversing on average
		// half of the list.
		
		found	= false;
		if(location == null || target.compareTo(location.getInfo()) < 0) // first checks to see if it is before or after the 
		{																 // last location of the inseted node
			location = list;
			while (location != null && location.getInfo().compareTo(target) <= 0)
			{
				compare++;
				if (location.getInfo().equals(target))	// if the word is found in the list 
				{
					found = true;
					return;
				}
				else
				{
					previous = location;			// moves previous to location
					location = location.getLink();	// location moves to next node
				}
			}
		}
		else	// comes to this statement if the target word is after the previous location we collected 
		{		// this is the best case cause then it's possible we start half way through the list
			while (location != null && location.getInfo().compareTo(target) <= 0)
			{
				compare++;
				if (location.getInfo().equals(target)) // if the word is found in the list 
				{
					found = true;
					return;
				}
				else
				{
					if(location.getCount() == 0) // makes sure that the reference of location doesn't still point to the 
					{							 // node that was removed from the list
						location = location.getLink();
						continue;
					}
					previous = location;			// moves previous to location
					location = location.getLink();	// location moves to next node
				}
			}
		}
	}
}
