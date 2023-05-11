package edu.uncc.finalexam;

public class Nft {

    String image;
    String name;
    String collectionname;
    String collectionimage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public Nft() {
    }

    public Nft(String image, String name, String collectionname, String collectionimage, String id) {
        this.image = image;
        this.name = name;
        this.collectionname = collectionname;
        this.collectionimage = collectionimage;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollectionname() {
        return collectionname;
    }

    public void setCollectionname(String collectionname) {
        this.collectionname = collectionname;
    }

    public String getCollectionimage() {
        return collectionimage;
    }

    public void setCollectionimage(String collectionimage) {
        this.collectionimage = collectionimage;
    }
}
