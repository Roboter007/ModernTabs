<hr>
<h1 style="text-align: left;"><strong>What is ModernTabs?</strong></h1>
<hr>
<p>It is a library mod that adds a <strong>cross-platform</strong> solution for <strong>customzing any creative tab</strong> even further than what is currently possible on both NeoForge and Fabric.<br>ModernTabs makes it possible to add individual <strong>sections</strong> with an assigned<strong> banner</strong>, change the <strong>scrollbar</strong> sprite and <strong>modify the background sprite</strong> for the creative tab icon. Furthermore, you can use <strong>any sprite as a creative tab icon</strong> without it having to be registered as an item in game.&nbsp;<br>ModernTabs also makes it possible to <strong>change the background</strong>,<strong> icon background</strong> and <strong>scroller texture</strong> just by <strong>defining the color</strong> you want without any texture sprite required.<br><br>The idea for the creative tab banners comes from the <a href="https://www.curseforge.com/minecraft/mc-mods/create-aeronautics" target="_blank" rel="nofollow noopener">Create Aeronautics/Simulated</a> mod and therefore includes little portions of its code.</p>
<p>&nbsp;</p>
<hr>
<h1><strong>Example Creative Tab</strong></h1>
<hr>
<p><img src="https://media.forgecdn.net/attachments/description/1606742/description_2971c19c-385a-4583-846e-e1544d97306e.png"></p>
<p>This example creative tab is only enabled in the development enviroment and therefore exists for showcase only. <br>That means it will not show up in a regular modpack.</p>
<p>&nbsp;</p>
<hr>
<h1><strong>For Mod Developers</strong></h1>
<hr>
<div class="spoiler">
<h2><span style="text-decoration: underline;">How to use ModernTabs in your own mod (Setup):</span></h2>
<p>You have to define this in your&nbsp;<span style="text-decoration: underline;">build.gradle</span> file:</p>
<div>
<pre>repositories {<br>    maven {<br>        name = "Jitpack"<br>        url 'https://jitpack.io'<br>    }<br>}<br><br>dependencies {<br>  // Only required in a multiloader setup (use this in your common part of your mod)<br>  // contains the common module<br>  modImplementation("com.github.Roboter007.ModernTabs:ModernTabs-common:${rootProject.moderntabs_version}")<br><br>  // Required on Fabric<br>  // contains the Fabric specific module including the common module<br>  modImplementation("com.github.Roboter007.ModernTabs:ModernTabs-fabric:${rootProject.moderntabs_version}")<br><br>  // Required on NeoForge<br>  // contains the NeoForge specific module including the common module<br> &nbsp;modImplementation("com.github.Roboter007.ModernTabs:ModernTabs-neoforge:${rootProject.moderntabs_version}")<br>} </pre>
</div>
<p>Then you can define this in your <span style="text-decoration: underline;">gradle.properties</span> file:</p>
<div>
<pre>// for the latest version:<br>moderntabs_version=v1.5.0</pre>
</div>
<p>&nbsp;</p>
<h2><span style="text-decoration: underline;">What you need to do in order to correctly customize creative tabs with your mod:</span></h2>
<pre>// The TabIconBackground class combines any possible state of the tab icon background <br>// It uses the default vanilla sprite if it doesn't find a sprite in the given location<br>// the sprites for the icon background has to be located under textures/gui/sprites/creative_inventory/<br>// the sprite file for the icon background depends on: tab_tabIdentifer_row_column_selectionState.png<br>// example file name: tab_example_top_left_selected.png<br>TabIconBackground exampleTabBackgrounds = new TabIconBackground(ExampleMod.MOD_ID, "example"); // example is the tabIdentifier<br><br>// in this example the first argument represents the text background color<br>// the second argument defines the location of the sprite file and the third and fourth defines the rendered width and height of the example_titel.png<br>// the sprite file has to be in the sprites folder (in this example under: textures/gui/sprites/creative_inventory/example_titel.png)<br>SpriteTabTitel spriteTabTitel = new SpriteTabTitel(new ModernColor("#36454F").lighten(0.5f), ModernTabs.path("container/creative_inventory/example_titel"), 64, 10);<br><br>// just an example color<br>ModernColor exampleColor = new ModernColor("#36454F");<br><br>// use your already registered creative tab (in this example: EXAMPLE_TAB)<br>ModernTabs.builder(EXAMPLE_TAB)<br>        // makes it possible for the defined creative tab to use custom sections<br>        .withEnabledSections(true)<br>        // changes the tab icon background sprite in any given state<br>        .withCustomTabIconBackground(exampleTabBackgrounds)<br> &nbsp; &nbsp; &nbsp; &nbsp;// displays a sprite instance of an regular Minecraft item.<br>        // the sprite for the custom icon has to be located under textures/gui/sprites/<br>        .withCustomTabIcon(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "example_icon"))<br>        // changes the sprite for the scrollbar<br> &nbsp; &nbsp; &nbsp; &nbsp;// the scrollbar for the custom icon has to be located under textures/gui/sprites/<br>        .withCustomScroller(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "example_scroller"))<br>        // modifies your creative tab titel<br>        // options: 1) aura text style (equivalent to the text of the creative tab banners)<br>        //          2) sprite (uses a sprite as your creative tab titel)<br>        //          3) custom (uses the default vanilla style text rendering, but with more configuration options like the text color)<br>        .withCustomTitelRendering(spriteTabTitel)<br>        // sets only the creative tab background texture color and the scrollbar texture color<br>        .withCustomBackgroundColor(exampleColor)<br>        // this method does the same as the withCustomBackgroundColor method, but it also changes the tab icon background color<br>        .withCustomColor(exampleColor);<br><br>// this defines which items are included in which section<br>// for creating a section you need to define a json file in your assets folder (scroll down a little, if you want to know how create a section)<br>SectionedItems.addItem(ExampleItems.EXAMPLE_ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "example_section"));<br><br>// hide a debug item from the grid but keep it searchable<br>TabItemTransforms.setVisibility(MyItems.DEBUG_STICK, TabItemTransforms.VisibilityType.SEARCH_ONLY);</pre>
<p>&nbsp;</p>
<p>In order for the creative tab sections to work properly, you need to create one&nbsp;<strong>JSON</strong> file for each creative tab section you want to have.<br>These json files have to be in the location: <strong>assets/mymod/moderntabs/sections/example_section.json</strong> and the defined section in your code has to match one json file in this location.</p>
<p>Example <strong>JSON</strong> file:</p>
<pre>{<br>&nbsp; "priority": 0,<br>&nbsp; "title": {<br>  &nbsp; "text": { "translate": "itemGroup.example_mod.basics" },<br>&nbsp; &nbsp; "color": "#FFFFFF",<br>  &nbsp; "background": "#AA000000",<br>&nbsp; },<br>  "sprite": "example_mod:basics_banner",<br>  "animation_mode": "play_on_hover"<br>}</pre>
<table style="width: 100%; height: 531.984px;">
<thead>
<tr style="height: 21px;">
<th style="width: 16.9894%; height: 21px;">Field</th>
<th style="width: 17.8695%; height: 21px;">Type</th>
<th style="width: 19.5425%; height: 21px;">Default</th>
<th style="width: 45.5986%; height: 21px;">Notes</th>
</tr>
</thead>
<tbody>
<tr style="height: 44.1875px;">
<td style="width: 16.9894%; height: 44.1875px;"><code>priority</code></td>
<td style="width: 17.8695%; height: 44.1875px;">positive int</td>
<td style="width: 19.5425%; height: 44.1875px;"><code>0</code></td>
<td style="width: 45.5986%; height: 44.1875px;">Lower values are drawn first (higher up in the tab).</td>
</tr>
<tr style="height: 44.1875px;">
<td style="width: 16.9894%; height: 44.1875px;"><code>title.text</code></td>
<td style="width: 17.8695%; height: 44.1875px;">Component</td>
<td style="width: 19.5425%; height: 44.1875px;"><em>required</em></td>
<td style="width: 45.5986%; height: 44.1875px;">Any normal text/translatable component.</td>
</tr>
<tr style="height: 70.5px;">
<td style="width: 16.9894%; height: 70.5px;"><code>title.color</code></td>
<td style="width: 17.8695%; height: 70.5px;"><span style="font-family: monospace;">html color code:<br><code>"#RRGGBB"</code><code>"#AARRGGBB"</code><br></span></td>
<td style="width: 19.5425%; height: 70.5px;"><code>#FFFFFFFF</code></td>
<td style="width: 45.5986%; height: 70.5px;">Primary text fill color.</td>
</tr>
<tr style="height: 70.5px;">
<td style="width: 16.9894%; height: 70.5px;"><code>title.secondary_color</code></td>
<td style="width: 17.8695%; height: 70.5px;"><span style="font-family: monospace;">html color code</span></td>
<td style="width: 19.5425%; height: 70.5px;">20% darker than <code>title.color</code></td>
<td style="width: 45.5986%; height: 70.5px;">Outline/"aura" color.</td>
</tr>
<tr style="height: 70.5px;">
<td style="width: 16.9894%; height: 70.5px;"><code>title.background</code></td>
<td style="width: 17.8695%; height: 70.5px;"><code>html color code</code></td>
<td style="width: 19.5425%; height: 70.5px;"><code>#AA000000</code></td>
<td style="width: 45.5986%; height: 70.5px;">Background color of the pill behind the text.</td>
</tr>
<tr style="height: 72.125px;">
<td style="width: 16.9894%; height: 72.125px;"><code>title.orientation</code></td>
<td style="width: 17.8695%; height: 72.125px;">TextOrientation</td>
<td style="width: 19.5425%; height: 72.125px;">left</td>
<td style="width: 45.5986%; height: 72.125px;">Defines if the text gets placed left, centered or right on the banner</td>
</tr>
<tr style="height: 70.5px;">
<td style="width: 16.9894%; height: 70.5px;"><code>sprite</code></td>
<td style="width: 17.8695%; height: 70.5px;">sprite ID as a string</td>
<td style="width: 19.5425%; height: 70.5px;"><code>moderntabs:default_banner</code></td>
<td style="width: 45.5986%; height: 70.5px;">A texture under <code>textures/gui/sprites/</code>, 162&times;18 px per animation frame.</td>
</tr>
<tr style="height: 68.4844px;">
<td style="width: 16.9894%; height: 68.4844px;"><code>animation_mode</code></td>
<td style="width: 17.8695%; height: 68.4844px;">BannerAnimationMode</td>
<td style="width: 19.5425%; height: 68.4844px;"><span style="font-family: monospace;">not_animated</span></td>
<td style="width: 45.5986%; height: 68.4844px;">Defines how the banner animation gets treated. Possible inputs are: not_animated, play_on_hover, play_continuously</td>
</tr>
</tbody>
</table>
</div>
<p>&nbsp;</p>
<hr>
<h1><strong>For Modpack/KubeJs Developers</strong></h1>
<hr>
<div class="spoiler">
<h2><span style="text-decoration: underline;">How to use ModernTabs in your own mod (Setup):</span></h2>
<p>At first, download the latest <a title="KubeJs" href="https://modrinth.com/mod/kubejs/versions" target="_blank" rel="noopener">KubeJs</a> and <a title="ModernTabs" href="https://modrinth.com/mod/moderntabs/versions" target="_blank" rel="noopener">ModernTabs</a> version.</p>
<p>&nbsp;</p>
<h2><span style="text-decoration: underline;">What you need to do in order to correctly customize the creative tabs you want to modify:</span></h2>
<div>&nbsp;You can simply create a new JavaScript/js file in the startup_scripts folder. This code can be used as an orientation:</div>
<div>
<pre>// The TabIconBackground class combines any possible state of the tab icon background<br>// It uses the default vanilla sprite if it doesn't find a sprite in the given location<br>// the sprites for the icon background has to be located under textures/gui/sprites/creative_inventory/<br>// the sprite file for the icon background depends on: tab_tabIdentifer_row_column_selectionState.png<br>// example file name: tab_example_top_left_selected.png<br>const exampleTabBackgrounds = new TabIconBackground("examplemod", "example"); // example is the tabIdentifier<br><br>// in this example the first argument represents the text background color<br>// the second argument defines the location of the sprite file and the third and fourth defines the rendered width and height of the example_titel.png<br>// the sprite file has to be in the sprites folder (in this example under: textures/gui/sprites/creative_inventory/example_titel.png)<br>const spriteTabTitel = new SpriteTabTitel(new ModernColor("#36454F").lighten(0.5f), ModernTabs.path("container/creative_inventory/example_titel"), 64, 10);<br><br>// just an example color<br>const exampleColor = new ModernColor("#36454F");<br><br>// use your already registered creative tab (in this example: EXAMPLE_TAB)<br>ModernTabs.builder("examplemod:example_tab")<br>        // makes it possible for the defined creative tab to use custom sections<br>&nbsp; &nbsp; &nbsp; &nbsp; .withEnabledSections(true)<br>&nbsp; &nbsp; &nbsp; &nbsp; // changes the tab icon background sprite in any given state<br>&nbsp; &nbsp; &nbsp; &nbsp; .withCustomTabIconBackground(exampleTabBackgrounds)<br>&nbsp; &nbsp; &nbsp; &nbsp; // displays a sprite instance of an regular Minecraft item.<br>&nbsp; &nbsp; &nbsp; &nbsp; // the sprite for the custom icon has to be located under textures/gui/sprites/<br>&nbsp; &nbsp; &nbsp; &nbsp; .withCustomTabIcon("examplemod:example_icon")<br>&nbsp; &nbsp; &nbsp; &nbsp; // changes the sprite for the scrollbar<br>&nbsp; &nbsp; &nbsp; &nbsp; // the scrollbar for the custom icon has to be located under textures/gui/sprites/<br>&nbsp; &nbsp; &nbsp; &nbsp; .withCustomScroller("examplemod:example_scroller")<br>&nbsp; &nbsp; &nbsp; &nbsp; // modifies your creative tab titel<br>&nbsp; &nbsp; &nbsp; &nbsp; // options: 1) aura text style (equivalent to the text of the creative tab banners)<br>&nbsp; &nbsp; &nbsp; &nbsp; // &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2) sprite (uses a sprite as your creative tab titel)<br>&nbsp; &nbsp; &nbsp; &nbsp; // &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3) custom (uses the default vanilla style text rendering, but with more configuration options like the text color)<br>&nbsp; &nbsp; &nbsp; &nbsp; .withCustomTitelRendering(new SpriteTabTitel(new ModernColor("#36454F").lighten(0.5f), ModernTabs.path("container/creative_inventory/example_titel"), 64, 10))<br>&nbsp; &nbsp; &nbsp; &nbsp; // sets only the creative tab background texture color and the scrollbar texture color<br>&nbsp; &nbsp; &nbsp; &nbsp; .withCustomBackgroundColor(exampleColor)<br>&nbsp; &nbsp; &nbsp; &nbsp; // this method does the same as the withCustomBackgroundColor method, but it also changes the tab icon background color<br>&nbsp; &nbsp; &nbsp; &nbsp; .withCustomColor(exampleColor);<br><br>// this defines which items are included in which section<br>// for creating a section you need to define a json file in your assets folder (scroll down a little, if you want to know how create a section)<br>SectionedItems.addItem(Item.of("examplemod:example_item"), "examplemod:example_section");<br><br>// hide a debug item from the grid but keep it searchable (here a grass block as an example)<br>TabItemTransforms.setVisibility(Item.of("minecraft:grass_block"), "SEARCH_ONLY");&nbsp;</pre>
</div>
<p>&nbsp;</p>
<p>In order for the creative tab sections to work properly, you need to create one&nbsp;<strong>JSON</strong> file for each creative tab section you want to have.<br>These json files have to be in the location: <strong>assets/mymod/moderntabs/sections/example_section.json</strong> and the defined section in your code has to match one json file in this location.</p>
<p>Example <strong>JSON</strong> file:</p>
<pre>{<br>&nbsp; "priority": 0,<br>&nbsp; "title": {<br>  &nbsp; "text": { "translate": "itemGroup.example_mod.basics" },<br>&nbsp; &nbsp; "color": "#FFFFFF",<br>  &nbsp; "background": "#AA000000",<br>&nbsp; },<br>  "sprite": "example_mod:basics_banner",<br>  "animation_mode": "play_on_hover"<br>}</pre>
<table style="width: 100%; height: 531.984px;">
<thead>
<tr style="height: 21px;">
<th style="width: 16.9894%; height: 21px;">Field</th>
<th style="width: 17.8695%; height: 21px;">Type</th>
<th style="width: 19.5425%; height: 21px;">Default</th>
<th style="width: 45.5986%; height: 21px;">Notes</th>
</tr>
</thead>
<tbody>
<tr style="height: 44.1875px;">
<td style="width: 16.9894%; height: 44.1875px;"><code>priority</code></td>
<td style="width: 17.8695%; height: 44.1875px;">positive int</td>
<td style="width: 19.5425%; height: 44.1875px;"><code>0</code></td>
<td style="width: 45.5986%; height: 44.1875px;">Lower values are drawn first (higher up in the tab).</td>
</tr>
<tr style="height: 44.1875px;">
<td style="width: 16.9894%; height: 44.1875px;"><code>title.text</code></td>
<td style="width: 17.8695%; height: 44.1875px;">Component</td>
<td style="width: 19.5425%; height: 44.1875px;"><em>required</em></td>
<td style="width: 45.5986%; height: 44.1875px;">Any normal text/translatable component.</td>
</tr>
<tr style="height: 70.5px;">
<td style="width: 16.9894%; height: 70.5px;"><code>title.color</code></td>
<td style="width: 17.8695%; height: 70.5px;"><span style="font-family: monospace;">html color code:<br><code>"#RRGGBB"</code><code>"#AARRGGBB"</code><br></span></td>
<td style="width: 19.5425%; height: 70.5px;"><code>#FFFFFFFF</code></td>
<td style="width: 45.5986%; height: 70.5px;">Primary text fill color.</td>
</tr>
<tr style="height: 70.5px;">
<td style="width: 16.9894%; height: 70.5px;"><code>title.secondary_color</code></td>
<td style="width: 17.8695%; height: 70.5px;"><span style="font-family: monospace;">html color code</span></td>
<td style="width: 19.5425%; height: 70.5px;">20% darker than <code>title.color</code></td>
<td style="width: 45.5986%; height: 70.5px;">Outline/"aura" color.</td>
</tr>
<tr style="height: 70.5px;">
<td style="width: 16.9894%; height: 70.5px;"><code>title.background</code></td>
<td style="width: 17.8695%; height: 70.5px;"><code>html color code</code></td>
<td style="width: 19.5425%; height: 70.5px;"><code>#AA000000</code></td>
<td style="width: 45.5986%; height: 70.5px;">Background color of the pill behind the text.</td>
</tr>
<tr style="height: 72.125px;">
<td style="width: 16.9894%; height: 72.125px;"><code>title.orientation</code></td>
<td style="width: 17.8695%; height: 72.125px;">TextOrientation</td>
<td style="width: 19.5425%; height: 72.125px;">left</td>
<td style="width: 45.5986%; height: 72.125px;">Defines if the text gets placed left, centered or right on the banner</td>
</tr>
<tr style="height: 70.5px;">
<td style="width: 16.9894%; height: 70.5px;"><code>sprite</code></td>
<td style="width: 17.8695%; height: 70.5px;">sprite ID as a string</td>
<td style="width: 19.5425%; height: 70.5px;"><code>moderntabs:default_banner</code></td>
<td style="width: 45.5986%; height: 70.5px;">A texture under <code>textures/gui/sprites/</code>, 162&times;18 px per animation frame.</td>
</tr>
<tr style="height: 68.4844px;">
<td style="width: 16.9894%; height: 68.4844px;"><code>animation_mode</code></td>
<td style="width: 17.8695%; height: 68.4844px;">BannerAnimationMode</td>
<td style="width: 19.5425%; height: 68.4844px;"><span style="font-family: monospace;">not_animated</span></td>
<td style="width: 45.5986%; height: 68.4844px;">Defines how the banner animation gets treated. Possible inputs are: not_animated, play_on_hover, play_continuously</td>
</tr>
</tbody>
</table>
</div>
<p><span style="text-decoration: underline;">&nbsp;</span></p>
<hr>
<h1><strong>FAQ</strong></h1>
<hr>
<p><span style="text-decoration: underline;">Why another such mod that adds creative tab banners?</span></p>
<p>➔ I created this library so I can use it for some upcoming mods by me and therefore, I need both a supported NeoForge and Fabric version. <br>Additionly, it already contains many other features with the goal of making the creative tab easier to configure.</p>
<p><span style="text-decoration: underline;">Will the Minecraft version x be supported?</span></p>
<p>➔ I will not backport it. If anyone plans on doing it, feel free to do it and if you want, you can create a pull request on GitHub. I might update it to newer versions in the future (but I will not guarantee it).</p>
<p>&nbsp;</p>
<hr>
<h1>Bugs &amp; Feature Requests</h1>
<hr>
<p>Please report any bugs you encounter or feature requests on the linked GitHub.</p>
<p>&nbsp;</p>
<hr>
<h1><strong>Credits</strong></h1>
<hr>
<ul>
<li>Thank you to the <a href="https://www.curseforge.com/minecraft/mc-mods/create-aeronautics" target="_blank" rel="nofollow noopener">Create Aeronautics/Simulated</a> mod team and contributors for creating such a great mod.</li>
</ul>