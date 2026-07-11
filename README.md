<hr>
<h1 style="text-align: left;"><strong>What is ModernTabs?</strong></h1>
<hr>
<p>It is a library which adds creative mode tab banners which are purely for decorational purposes. Moreover it also contains specific parts of code from the Create Aeronautics/Simulated Mod.</p>
<p>&nbsp;</p>
<hr>
<h1><strong>Example Creative Tab</strong></h1>
<hr>
<p><img src="https://media.forgecdn.net/attachments/description/null/description_481190fc-36f3-4963-abe5-2f95d71279d0.png"></p>
<p>This example creative tab is disabled by default and can currently only be enabled by another mod.</p>
<p>&nbsp;</p>
<hr>
<h1><strong>For Developers</strong></h1>
<hr>
<p>You can use it in your mod by using cursemaven:</p>
<pre>repositories { <br>  maven { <br>    url "https://cursemaven.com" <br>  } <br>}</pre>
<p>&nbsp;</p>
<p><span style="text-decoration: underline;">What you need to do in order to implement the creative tab banners correctly in your mod:<br></span></p>
<pre>// Required: Once, e.g. right after you build your tab:<br>ModernTabs.enableSections(MyMod.MAIN_TAB);<br><br>// Required: For every item that should be grouped:<br>SectionedItems.addItem(MyItems.EXAMPLE_ITEM, ResourceLocation.fromNamespaceAndPath("mymod", "example_section"));<br><br>// Optional: hide a debug item from the grid but keep it searchable<br>TabItemTransforms.setVisibility(MyItems.DEBUG_STICK, TabItemTransforms.VisibilityType.SEARCH_ONLY);<br><br>// Optional: if you want to enable the example tab:<br>ModernTabs.setExampleTabEnabled(true);</pre>
<p>&nbsp;</p>
<p>In order for the creative tab sections to work properly, you need to create one <strong>JSON</strong> file for each creative tab section you want to have.<br>These json files have to be in the location: <strong>assets/mymod/moderntabs/sections/basics.json</strong> and the defined section (in this case: example_section) has to match one json file in this location</p>
<p>Example <strong>JSON</strong> file:</p>
<pre>{<br>&nbsp; "priority": 0,<br>&nbsp; "title": {<br>&nbsp; &nbsp; "text": { "translate": "itemGroup.mymod.basics" },<br>&nbsp; &nbsp; "color": "#FFFFFF",<br>&nbsp; &nbsp; "background": "#AA000000"<br>&nbsp; },<br>&nbsp; "sprite": "mymod:basics_banner",<br>&nbsp; "only_animate_on_hover": false<br>}</pre>
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
<p>&nbsp;</p>
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