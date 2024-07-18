package org.ipvp.canvas.paginate;

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
