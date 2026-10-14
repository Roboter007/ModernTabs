package de.Roboter007.moderntabs.platform;

public interface SearchbarPlatform {

    void moderntabs$setSearchbarEnabled(boolean hasSearchbar);
    boolean moderntabs$hasSearchbar();

    void moderntabs$setSearchbarEditBoxWidth(int searchbarLength);
    int moderntabs$getSearchbarEditBoxWidth();
}
