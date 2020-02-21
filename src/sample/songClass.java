// Haseeb Balal (hhb10) and Muffadal Hussain (mmh240)
package sample;

public class songClass
{
    String name;
    String artist;
    String album;
    String year;

    public songClass(Object o, Object o1, Object o2, Object o3)
    {
        name = "";
        artist = "";
        album = "";
        year = "";
    }

    public songClass(String name, String artist, String album, String year)
    {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String toString()
    {
        return "Song" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", year=" + year ;
    }
    public String toString2()
    {
        return  name + "               "+
                artist ;
    }
//    public boolean add(String name, String artist, String album, String year) {
//
//        if((name.isEmpty() || artist.isEmpty()) || (name.isBlank() || artist.isBlank())) {
//            return false;
//        }
//
//        Song sng = new Song(name,artist,album,year);
//        for(Song s : songList) {
//            if(s.isDuplicate(sng)) {
//                return false;
//            }
//			/*if(s.getName() == name) {
//
//			}*/
//        }
//        return songList.add(sng);
//    }
//
//    public boolean edit(Song sng, String name, String artist, String album, String year) {
//        songList.remove(sng);
//        if(!add(name,artist,album,year)) {
//            add(sng.getName(),sng.getArtist(),sng.getAlbum(),sng.getYear());
//            return false;
//        }
//        return true;
//    }
//
//    public boolean delete(Song sng) {
//        if(songList.contains(sng)){
//            songList.remove(sng);
//            return true;
//        } else {
//            return false;
//        }
//    }


}