package hunkydory.model;

@SuppressWarnings("unused")
public class Category {
    private int categoryID;    // id_categoria (PK)
    private String description; // descricao

    public Category() {
    }

    public Category(int categoryID, String description) {
        this.categoryID = categoryID;
        this.description = description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category [categoryID=" + categoryID + ", description=" + description + "]";
    }
}
