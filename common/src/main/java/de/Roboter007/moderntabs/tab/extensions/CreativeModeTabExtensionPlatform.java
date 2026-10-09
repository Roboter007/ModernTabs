package de.Roboter007.moderntabs.tab.extensions;

public interface CreativeModeTabExtensionPlatform {

    void moderntabs$setSearchbarEnabled(boolean hasSearchbar);
    boolean moderntabs$hasSearchbar();

    void moderntabs$setSearchbarEditBoxWidth(int searchbarLength);
    int moderntabs$getSearchbarEditBoxWidth();
}
