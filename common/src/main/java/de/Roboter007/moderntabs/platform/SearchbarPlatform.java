package de.Roboter007.moderntabs.platform;

// only exists because there is already a light implementation for search bars on NeoForge
public interface SearchbarPlatform {

    void moderntabs$setSearchbarEnabled(boolean hasSearchbar);
    boolean moderntabs$hasSearchbar();

    void moderntabs$setSearchbarEditBoxWidth(int searchbarLength);
    int moderntabs$getSearchbarEditBoxWidth();
}
