// Haseeb Balal (hhb10) and Muffadal Hussain (mmh240)
package sample;
import java.util.*;
public class sort implements Comparator<songClass>
{
    public int compare(songClass a, songClass b)
    {
        if(a.name.toUpperCase().compareTo(b.name.toUpperCase()) == 0)
            return a.artist.toUpperCase().compareTo(b.artist.toUpperCase());

        return a.name.toUpperCase().compareTo(b.name.toUpperCase());
    }

}