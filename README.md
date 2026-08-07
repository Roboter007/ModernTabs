<hr>
<h1 style="text-align: left;"><strong>What is ModernTabs?</strong></h1>
<hr>
<p>It is a library mod that adds a <strong>cross-platform</strong> solution for <strong>customzing any creative tab</strong> even further than what is currently possible on both NeoForge and Fabric.<br>ModernTabs makes it possible to add individual <strong>sections</strong> with an assigned<strong> banner</strong>, change the <strong>scrollbar</strong> sprite, modify the tab icon background sprite and adds an option for using any sprite as a tab icon.<br>The idea for the creative tab banners comes from the <a href="https://www.curseforge.com/minecraft/mc-mods/create-aeronautics" target="_blank" rel="nofollow noopener">Create Aeronautics/Simulated</a>&nbsp;mod and therefore includes portions of its code.</p>
<p>&nbsp;</p>
<hr>
<h1><strong>Example Creative Tab</strong></h1>
<hr>
<p><img src="https://media.forgecdn.net/attachments/description/null/description_481190fc-36f3-4963-abe5-2f95d71279d0.png" alt=""></p>
<p>This example creative tab is only enabled in the development enviroment. That means it will not show up in a regular modpack.</p>
<p>&nbsp;</p>
<hr>
<h1><strong>For Developers</strong></h1>
<hr>
<h2><span style="text-decoration: underline;">How to use ModernTabs in your own mod (Setup):</span></h2>
<p>You have to define this in your&nbsp;<span style="text-decoration: underline;">build.gradle</span> file:</p>
<div>
<pre>repositories {<br>    maven {<br>        name = "Jitpack"<br>        url 'https://jitpack.io'<br>    }<br>}<br><br>dependencies {<br>  // Required: contains the common module used on both platoforms and therefore most of this mod's code<br>  modImplementation("com.github.Roboter007.ModernTabs:ModernTabs-common:${rootProject.moderntabs_version}")<br><br>  // Optional: contains the Fabric specific module<br>  modImplementation("com.github.Roboter007.ModernTabs:ModernTabs-fabric:${rootProject.moderntabs_version}")<br><br>  // Optional: contains the NeoForge specific module<br> &nbsp;modImplementation("com.github.Roboter007.ModernTabs:ModernTabs-neoforge:${rootProject.moderntabs_version}")<br>} </pre>
</div>
<p>Then you can define this in your <span style="text-decoration: underline;">gradle.properties</span> file:</p>
<div>
<pre>// for the latest version:<br>moderntabs_version=v1.2.0</pre>
</div>
<p>&nbsp;</p>
<h2><span style="text-decoration: underline;">What you need to do in order to implement the creative tab banners correctly in your mod:</span></h2>
<pre>// The TabIconBackground class combines any possible state of the tab icon background <br>// It uses the default vanilla sprite if it doesn't find a sprite in the given location<br>// the sprites for the icon background has to be located under textures/gui/sprites/creative_inventory/<br>// the sprite file for the icon background depends on: tab_tabIdentifer_row_column_selectionState.png<br>// example file name: tab_example_top_left_selected.png<br>TabIconBackground exampleTabBackgrounds = new TabIconBackground(ExampleMod.MOD_ID, "example"); // example is the tabIdentifier<br><br>ModernTabs.builder(EXAMPLE_TAB)<br>        // makes it possible for the defined creative tab to use custom sections<br>        .withEnabledSections(true)<br>        // changes the tab icon background sprite in any given state<br>        .withCustomTabIconBackground(exampleTabBackgrounds)<br> &nbsp; &nbsp; &nbsp; &nbsp;// displays a sprite instance of an regular Minecraft item.<br>        // the sprite for the custom icon has to be located under textures/gui/sprites/<br>        .withCustomTabIcon(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "example_icon"))<br>        // changes the sprite for the scrollbar<br> &nbsp; &nbsp; &nbsp; &nbsp;// the scrollbar for the custom icon has to be located under textures/gui/sprites/<br>        .withCustomScroller(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "example_scroller"));<br><br>// this defines which items are included in which section<br>// for creating a section you need to define a json file in your assets folder (scroll down a little, if you want to know how create a section)<br>SectionedItems.addItem(ExampleItems.EXAMPLE_ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "example_section"));<br><br>// hide a debug item from the grid but keep it searchable<br>TabItemTransforms.setVisibility(MyItems.DEBUG_STICK, TabItemTransforms.VisibilityType.SEARCH_ONLY);</pre>
<p>&nbsp;</p>
<p>In order for the creative tab sections to work properly, you need to create one&nbsp;<strong>JSON</strong> file for each creative tab section you want to have.<br>These json files have to be in the location: <strong>assets/mymod/moderntabs/sections/example_section.json</strong> and the defined section in your code has to match one json file in this location.</p>
<p>Example <strong>JSON</strong> file:</p>
<pre>{<br>&nbsp; "priority": 0,<br>&nbsp; "title": {<br>  &nbsp; "text": { "translate": "itemGroup.example_mod.basics" },<br>&nbsp; &nbsp; "color": "#FFFFFF",<br>&nbsp; &nbsp; "background": "#AA000000"<br>&nbsp; },<br>  "sprite": "example_mod:basics_banner",<br>&nbsp; "only_animate_on_hover": false<br>}</pre>
<table>
<thead>
<tr>
<th>Field</th>
<th>Type</th>
<th>Default</th>
<th>Notes</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>priority</code></td>
<td>positive int</td>
<td><code>0</code></td>
<td>Lower values are drawn first (higher up in the tab).</td>
</tr>
<tr>
<td><code>title.text</code></td>
<td>component</td>
<td><em>required</em></td>
<td>Any normal text/translatable component.</td>
</tr>
<tr>
<td><code>title.color</code></td>
<td><code>"#RRGGBB"</code> / <code>"#AARRGGBB"</code></td>
<td><code>#FFFFFFFF</code></td>
<td>Primary text fill color.</td>
</tr>
<tr>
<td><code>title.secondary_color</code></td>
<td><code>"#RRGGBB"</code> / <code>"#AARRGGBB"</code></td>
<td>20% darker than <code>title.color</code></td>
<td>Outline/"aura" color.</td>
</tr>
<tr>
<td><code>title.background</code></td>
<td><code>"#RRGGBB"</code> / <code>"#AARRGGBB"</code></td>
<td><code>#AA000000</code></td>
<td>Background color of the pill behind the text.</td>
</tr>
<tr>
<td><code>sprite</code></td>
<td>GUI sprite ID</td>
<td><code>moderntabs:default_banner</code></td>
<td>A texture under <code>textures/gui/sprites/</code>, 162&times;18 px per animation frame.</td>
</tr>
<tr>
<td><code>only_animate_on_hover</code></td>
<td>boolean</td>
<td><code>false</code></td>
<td>If the sprite is animated, it will pause until the mouse hovers over the banner.</td>
</tr>
</tbody>
</table>
<h1>&nbsp;</h1>
<hr>
<h1><strong>FAQ</strong></h1>
<hr>
<p><span style="text-decoration: underline;">Why another such mod that adds creative tab banners?</span></p>
<p>➔ I created this library so I can use it for some upcoming mods by me and therefore, I need both a supported NeoForge and Fabric version. Additionly, I plan on adding some more features in the future.</p>
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