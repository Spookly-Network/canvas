/*
 * Copyright (C) Matthew Steglinski (SainttX) <matt@ipvp.org>
 * Copyright (C) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.spookly.canvas.slot;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import de.spookly.canvas.template.ItemStackTemplate;
import de.spookly.canvas.template.StaticItemTemplate;

/**
 * Utility class that holds the settings of a slot.
 */
public class SlotSettings {

    private ClickOptions clickOptions;
    private Slot.ClickHandler clickHandler;
    private ItemStackTemplate itemTemplate;
    private List<String> itemArguments;

    SlotSettings(ClickOptions clickOptions, Slot.ClickHandler clickHandler, ItemStackTemplate itemTemplate, List<String> itemArguments) {
        this.clickOptions = clickOptions;
        this.clickHandler = clickHandler;
        this.itemTemplate = itemTemplate;
        this.itemArguments = itemArguments;
    }

    /**
     * @see Slot#getClickOptions()
     */
    public ClickOptions getClickOptions() {
        return clickOptions;
    }

    /**
     * @see Slot#getClickHandler()
     */
    public Slot.ClickHandler getClickHandler() {
        return clickHandler;
    }

    /**
     * @see Slot#getItem(Player)
     */
    public ItemStackTemplate getItemTemplate() {
        return itemTemplate;
    }


    /**
     * @see Slot#getArguments()
     */
    public List<String> getItemArguments() {
        return itemArguments;
    }

    /**
     * Returns a new builder.
     *
     * @return builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for creating slot settings.
     */
    public static class Builder {

        private ClickOptions clickOptions;
        private Slot.ClickHandler clickHandler;
        private ItemStackTemplate itemTemplate;
        private List<String> itemArguments;

        private Builder() {

        }

        /**
         * @see Slot#setClickOptions(ClickOptions)
         */
        public Builder clickOptions(ClickOptions clickOptions) {
            this.clickOptions = clickOptions;
            return this;
        }

        /**
         * @see Slot#setClickHandler(Slot.ClickHandler)
         */
        public Builder clickHandler(Slot.ClickHandler clickHandler) {
            this.clickHandler = clickHandler;
            return this;
        }

        /**
         * Adds Arguments for slot handler
         * @param arguments
         * @return Builder
         */
        public Builder arguments(String... arguments) {
            this.itemArguments = Arrays.stream(arguments).collect(Collectors.toList());
            return this;
        }

        /**
         * @see Slot#setItem(ItemStack)
         */
        public Builder item(ItemStack itemStack) {
            return itemTemplate(itemStack == null ? null : new StaticItemTemplate(itemStack));
        }

        /**
         * @see #item(ItemStack)
         */
        public Builder item(Supplier<ItemStack> itemStack) {
            return item(itemStack.get());
        }

        /**
         * @see Slot#setItemTemplate(ItemStackTemplate)
         */
        public Builder itemTemplate(ItemStackTemplate itemTemplate) {
            this.itemTemplate = itemTemplate;
            return this;
        }

        /**
         * Builds a new slot details instance.
         *
         * @return slot details
         */
        public SlotSettings build() {
            return new SlotSettings(clickOptions, clickHandler, itemTemplate, itemArguments);
        }
    }
}
