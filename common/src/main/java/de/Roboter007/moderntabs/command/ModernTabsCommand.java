package de.Roboter007.moderntabs.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ModernTabsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> origin = Commands.literal("moderntabs");
        origin.then(Commands.literal("listTabs").then(Commands.argument("modId", StringArgumentType.string())
            .suggests((context, builder) -> SharedSuggestionProvider.suggest(ModernTabsPlatform.get().getLoadedMods(), builder))
            .executes(executeListModTabs())));

        dispatcher.register(origin);
    }

    private static Command<CommandSourceStack> executeListModTabs() {
        return context -> {
            String modId = StringArgumentType.getString(context, "modId");
            if(ModernTabsPlatform.get().getLoadedMods().contains(modId)) {
                int basicColor = new ModernColor("B5B5B5").color();
                int modIdColor = new ModernColor("F58727").color();
                int tabColor = new ModernColor("4DFFE7").color();

                MutableComponent tabs = Component.literal("\n");
                List<String> tabList = getTabsBy(modId);
                for(String tab : tabList) {
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, tab);
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("moderntabs.command.clickToCopy", Component.literal("\n" + tab).withColor(tabColor)).withColor(basicColor));

                    MutableComponent tabComponent = Component.literal(tab).withColor(tabColor)
                            .withStyle(style -> style.withClickEvent(clickEvent).withHoverEvent(hoverEvent));

                    if(!tab.equals(tabList.getLast())) {
                        tabs.append(tabComponent.append(Component.literal(", ").withColor(basicColor)));
                    } else {
                        tabs.append(tabComponent);
                    }
                }

                Component message = Component.translatable("moderntabs.command.tabList", Component.literal(modId).withColor(modIdColor))
                        .withColor(basicColor)
                        .append(tabs);

                context.getSource().sendSystemMessage(message);
            }
            return 0;
        };
    }

    public static List<String> getTabsBy(String modId) {
        return BuiltInRegistries.CREATIVE_MODE_TAB.keySet().stream().filter(id -> id.getNamespace().equals(modId)).map(ResourceLocation::toString).toList();
    }
}