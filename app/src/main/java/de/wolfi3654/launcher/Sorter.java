package de.wolfi3654.launcher;

/**
 * Created by root on 24.07.2016.
 */
public class Sorter {


    public static void sort(Pac... pacs){
        int i, j;
        Pac temp;

        for ( i = 0;  i < pacs.length - 1;  i++ )
        {
            for ( j = i + 1;  j < pacs.length;  j++ )
            {
                if ( pacs [ i ].label.compareToIgnoreCase( pacs [ j ].label ) > 0 )
                {                                             // ascending sort
                    temp = pacs [ i ];
                    pacs [ i ] = pacs [ j ];    // swapping
                    pacs [ j ] = temp;

                }
            }
        }


    }

}
