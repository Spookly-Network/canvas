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

package de.spookly.canvas.paginate;

import java.util.*;
import java.util.function.Consumer;

import de.spookly.canvas.Menu;
import de.spookly.canvas.mask.Mask;
import de.spookly.canvas.slot.Slot;
import de.spookly.canvas.template.ItemStackTemplate;
import de.spookly.canvas.template.StaticItemTemplate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract implementation of a PaginatedMenuBuilder, providing basic
 * method implementations.
 */
public abstract class AbstractPaginatedMenuBuilder<T extends AbstractPaginatedMenuBuilder<T>> {

    private final Menu.Builder<?> pageBuilder;
    private final List<Consumer<Menu>> newMenuModifiers;
    private List<Integer> previousButtonSlot = List.of(-1);
    private List<Integer> nextButtonSlot = List.of(-1);
    private ItemStackTemplate previousButton;
    private ItemStackTemplate previousButtonEmpty;
    private ItemStackTemplate nextButton;
    private ItemStackTemplate nextButtonEmpty;
    private PaginatedMenuTitles menuTitles;

    public AbstractPaginatedMenuBuilder(Menu.Builder<?> pageBuilder) {
        this.pageBuilder = pageBuilder;
        this.newMenuModifiers = new LinkedList<>();
    }

    /**
     * Gets the base builder for creating new pages.
     *
     * @return base page builder
     */
    public Menu.Builder<?> getPageBuilder() {
        return pageBuilder;
    }

    /**
     * Adds a modifier for when a new menu is created.
     *
     * @param newMenuModifier modifier
     * @return fluent pattern
     */
    public T newMenuModifier(Consumer<Menu> newMenuModifier) {
        if (newMenuModifier == null) {
            throw new IllegalArgumentException("Menu modifier cannot be null");
        }
        this.newMenuModifiers.add(newMenuModifier);
        return (T) this;
    }

    /**
     * Adds multiple modifiers for when a new menu is created.
     *
     * @param newMenuModifiers a collection of modifiers
     * @return fluent pattern
     */
    public T newMenuModifiers(Collection<Consumer<Menu>> newMenuModifiers) {
        newMenuModifiers.forEach(this::newMenuModifier);
        return (T) this;
    }

    /**
     * Gets the current modifiers for when a new menu is created.
     *
     * @return menu modifier
     */
    public List<Consumer<Menu>> getNewMenuModifiers() {
        return newMenuModifiers;
    }

    /**
     * @return previous page slot index
     * @deprecated use {@link #getPreviousButtonSlots()} instead
     * Gets the slot index for the previous page button.
     */
    @Deprecated
    public int getPreviousButtonSlot() {
        return previousButtonSlot.getFirst();
    }

    /**
     * Gets the slot index for the previous page button.
     *
     * @return previous page slot index
     */
    public List<Integer> getPreviousButtonSlots() {
        return previousButtonSlot;
    }

    /**
     * @param previousButtonSlot slot index
     * @return fluent pattern
     * @deprecated use {@link #previousButtonSlots(Integer...)} instead
     * Sets the slot index for the previous page button.
     */
    @Deprecated
    public T previousButtonSlot(int previousButtonSlot) {
        this.previousButtonSlot = new ArrayList<>(previousButtonSlot);
        return (T) this;
    }

    /**
     * Sets the slot index for the previous page button.
     *
     * @param previousButtonSlots slot index
     * @return fluent pattern
     */
    public T previousButtonSlots(Integer... previousButtonSlots) {
        this.previousButtonSlot = List.of(previousButtonSlots);
        return (T) this;
    }

    /**
     * Sets the slot index for the previous page button.
     *
     * <p>Only the first slot index in the mask will be taken.
     *
     * @param previousButtonSlot slot mask
     * @return fluent pattern
     */
    public T previousButtonSlot(Mask previousButtonSlot) {
        return previousButtonSlot(indexFromMask(previousButtonSlot));
    }

    /**
     * @return next page slot index
     * @deprecated use {@link #getNextButtonSlots()} instead
     * Gets the slot index for the next page button.
     */
    @Deprecated
    public int getNextButtonSlot() {
        return nextButtonSlot.getFirst();
    }

    /**
     * Gets the slot index for the next page button.
     *
     * @return next page slot index
     */
    public List<Integer> getNextButtonSlots() {
        return nextButtonSlot;
    }

    /**
     * @param nextButtonSlot slot index
     * @return fluent pattern
     * @deprecated use {@link #nextButtonSlots(Integer...)} instead
     * Sets the slot index for the next page button.
     */
    @Deprecated
    public T nextButtonSlot(int nextButtonSlot) {
        this.nextButtonSlot = List.of(nextButtonSlot);
        return (T) this;
    }

    /**
     * Sets the slot index for the next page button.
     *
     * @param nextButtonSlots slot index
     * @return fluent pattern
     */
    public T nextButtonSlots(Integer... nextButtonSlots) {
        this.nextButtonSlot = List.of(nextButtonSlots);
        return (T) this;
    }


    /**
     * Sets the slot index for the next page button.
     *
     * <p>Only the first slot index in the mask will be taken.
     *
     * @param nextButtonSlot slot mask
     * @return fluent pattern
     */
    public T nextButtonSlot(Mask nextButtonSlot) {
        return nextButtonSlot(indexFromMask(nextButtonSlot));
    }

    /* Helper method to get a slot index from a Mask2D */
    private static int indexFromMask(Mask mask) {
        if (mask.getSlots().isEmpty()) {
            return -1;
        }
        return mask.getSlots().iterator().next();
    }

    /**
     * Gets the icon for previous page button in the case that
     * there is no previous page.
     *
     * @return previous page button empty icon
     */
    public ItemStackTemplate getPreviousButton() {
        return previousButton;
    }

    /**
     * Sets the previous button icon.
     *
     * @param item icon
     * @return fluent pattern
     */
    public T previousButton(ItemStack item) {
        return previousButton(new StaticItemTemplate(item));
    }

    /**
     * Sets the previous button icon.
     *
     * @param item icon template
     * @return fluent pattern
     */
    public T previousButton(ItemStackTemplate item) {
        this.previousButton = item;
        return (T) this;
    }

    /**
     * Gets the icon for previous page button in the case that
     * there is no next page.
     *
     * @return previous page button empty icon
     */
    public ItemStackTemplate getPreviousButtonEmpty() {
        return previousButtonEmpty;
    }

    /**
     * Sets the previous button icon to display when there is no previous page.
     *
     * @param item icon
     * @return fluent pattern
     */
    public T previousButtonEmpty(ItemStack item) {
        return previousButtonEmpty(new StaticItemTemplate(item));
    }

    /**
     * Sets the previous button icon to display when there is no previous page.
     *
     * @param item icon template
     * @return fluent pattern
     */
    public T previousButtonEmpty(ItemStackTemplate item) {
        this.previousButtonEmpty = item;
        return (T) this;
    }

    /**
     * Gets the icon for next page button.
     *
     * @return next page button empty icon
     */
    public ItemStackTemplate getNextButton() {
        return nextButton;
    }

    /**
     * Sets the next button icon.
     *
     * @param item icon
     * @return fluent pattern
     */
    public T nextButton(ItemStack item) {
        return nextButton(new StaticItemTemplate(item));
    }

    /**
     * Sets the next button icon.
     *
     * @param item icon template
     * @return fluent pattern
     */
    public T nextButton(ItemStackTemplate item) {
        this.nextButton = item;
        return (T) this;
    }

    /**
     * Gets the icon for next page button in the case that
     * there is no next page.
     *
     * @return next page button empty icon
     */
    public ItemStackTemplate getNextButtonEmpty() {
        return nextButtonEmpty;
    }

    /**
     * Sets the next button icon to display when there is no next page.
     *
     * @param item icon
     * @return fluent pattern
     */
    public T nextButtonEmpty(ItemStack item) {
        return nextButtonEmpty(new StaticItemTemplate(item));
    }

    /**
     * Sets the next button icon to display when there is no next page.
     *
     * @param item icon template
     * @return fluent pattern
     */
    public T nextButtonEmpty(ItemStackTemplate item) {
        this.nextButtonEmpty = item;
        return (T) this;
    }

    public T paginateMenuTitles(PaginatedMenuTitles menuTitles) {
        this.menuTitles = menuTitles;
        return (T) this;
    }

    /**
     * Internal helper method to link any generated pages.
     *
     * @param pages pages to link
     */
    void linkPages(List<Menu> pages) {
        for (int i = 1; i < pages.size(); i++) {
            Menu page = pages.get(i);
            Menu prev = pages.get(i - 1);
            //TODO
            nextButtonSlot.forEach(nextIndex -> {
                setPaginationIcon(prev, nextIndex, nextButton, (p, c) -> page.open(p));
            });
            previousButtonSlot.forEach(prevIndex -> {
                setPaginationIcon(page, prevIndex, previousButton, (p, c) -> prev.open(p));
            });
        }
    }

    /**
     * Internal helper method to title any generated pages.
     *
     * @param pages pages to title
     */
    void setPageTitles(List<Menu> pages) {
        if (this.menuTitles != null) {
            if (pages.size() <= 1) {
                pages.getFirst().title(this.menuTitles.getNoPages());
                return;
            }
            for (int i = 0; i < pages.size(); i++) {
                Menu page = pages.get(i);
                if (i > 0 && i < pages.size() - 1) {
                    page.title(this.menuTitles.getPages());
                    continue;
                }
                if (i == 0) {
                    page.title(this.menuTitles.getFirstPage());
                    continue;
                }
                if (i == pages.size() - 1) {
                    page.title(this.menuTitles.getLastPage());
                }
            }
        }
    }

    /**
     * Internal helper method to set pagination icon with
     * validation checking.
     *
     * @param menu      menu to add
     * @param slotIndex slot index
     * @param icon      icon to set
     */
    void setPaginationIcon(Menu menu, int slotIndex, ItemStackTemplate icon) {
        setPaginationIcon(menu, slotIndex, icon, null);
    }

    /**
     * Internal helper method to set pagination icon with
     * validation checking.
     *
     * @param menu         menu to add
     * @param slotIndex    slot index
     * @param icon         icon to set
     * @param clickHandler click handler to apply
     */
    void setPaginationIcon(Menu menu, int slotIndex, ItemStackTemplate icon, Slot.ClickHandler clickHandler) {
        if (slotIndex >= 0 && slotIndex < menu.getDimensions().getArea()) {
            Slot slot = menu.getSlot(slotIndex);
            slot.setItemTemplate(icon);
            slot.setClickHandler(clickHandler);
        }
    }
}
