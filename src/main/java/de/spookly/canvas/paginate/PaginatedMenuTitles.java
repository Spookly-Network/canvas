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

import net.kyori.adventure.text.Component;

public class PaginatedMenuTitles {
    private Component pages;
    private Component firstPage;
    private Component lastPage;
    private Component noPages;

    protected PaginatedMenuTitles(final Component pages, final Component firstPage, final Component lastPage, final Component noPages) {
        this.pages = pages;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.noPages = noPages;
    }


    public Component getPages() {
        return pages;
    }

    public void setPages(Component pages) {
        this.pages = pages;
    }

    public Component getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Component firstPage) {
        this.firstPage = firstPage;
    }

    public Component getLastPage() {
        return lastPage;
    }

    public void setLastPage(Component lastPage) {
        this.lastPage = lastPage;
    }

    public Component getNoPages() {
        return noPages;
    }

    public void setNoPages(Component noPages) {
        this.noPages = noPages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Component pages;
        private Component firstPage;
        private Component lastPage;
        private Component noPages;

        public Builder pagesTitle(Component pages) {
            this.pages = pages;
            return this;
        }

        public Builder firstPageTitle(Component firstPage) {
            this.firstPage = firstPage;
            return this;
        }

        public Builder lastPageTitle(Component lastPage) {
            this.lastPage = lastPage;
            return this;
        }

        public Builder noPagesTitle(Component noPages) {
            this.noPages = noPages;
            return this;
        }

        public PaginatedMenuTitles build() {
            return new PaginatedMenuTitles(pages, firstPage, lastPage, noPages);
        }
    }
}
