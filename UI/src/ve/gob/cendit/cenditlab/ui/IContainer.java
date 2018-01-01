package ve.gob.cendit.cenditlab.ui;

import java.util.List;

public interface IContainer<T>
{
    void setAll(T... objects);
    void addAll(T... objects);
    void add(T object);

    T get(int index);
    Item<T> getItem(int index);
    List<T> getAll();
    List<Item<T>> getItems();


    List<T> getAllSelected();
    List<Item<T>> getAllSelectedItems();

    void remove(int index);
    void remove(T object);
    void removeAll(T... objects);

    void clear();

    void saveState();
    void restoreState();

}
