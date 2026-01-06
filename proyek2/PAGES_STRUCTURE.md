# Pages Folder Structure

## New Organization (Categorized by Website Menu)

```
src/test/java/com/proyek/pages/
│
├── product/                        # Product Menu Pages
│   ├── HelpdeskPage.java          # Helpdesk IT Management
│   ├── CmdbPage.java              # CMDB & Asset Management
│   ├── AdministrationPage.java    # User Management
│   ├── FinancePage.java           # Financial Management
│   ├── RoadmapPage.java           # Product Roadmap
│   ├── FeaturesPage.java          # Our Features
│   └── IntegrationsPage.java      # Integrations
│
├── resources/                      # Resources Menu Pages
│   ├── DownloadPage.java          # Download GLPI
│   ├── CommunityPage.java         # Community
│   ├── FaqPage.java               # FAQ
│   ├── PartnerPage.java           # Partners
│   ├── BlogPage.java              # Blog
│   └── TrainingPage.java          # Training
│
├── company/                        # Company Menu Pages
│   ├── AboutUsPage.java           # About Teclib'
│   ├── RndPage.java               # R&D
│   └── CustomerPage.java          # Customers
│
├── pricing/                        # Standalone Page
│   └── PricesPage.java            # Pricing Plans
│
├── common/                         # Shared Components
│   ├── HomePage.java              # Main Landing Page
│   ├── HeaderPage.java            # Header Navigation
│   └── FooterPage.java            # Footer Links
│
└── auth/                           # Authentication Pages
    ├── LoginPage.java             # Login Page
    └── RegistrationPage.java      # Registration Page
```

## Package Structure

### Product Pages
```java
package com.proyek.pages.product;
```

### Resources Pages
```java
package com.proyek.pages.resources;
```

### Company Pages
```java
package com.proyek.pages.company;
```

### Pricing Pages
```java
package com.proyek.pages.pricing;
```

### Common Pages
```java
package com.proyek.pages.common;
```

### Authentication Pages
```java
package com.proyek.pages.auth;
```

## Import Examples in Test Files

### Before (Old):
```java
import com.proyek.pages.HelpdeskPage;
import com.proyek.pages.LoginPage;
import com.proyek.pages.HomePage;
```

### After (New):
```java
import com.proyek.pages.product.HelpdeskPage;
import com.proyek.pages.auth.LoginPage;
import com.proyek.pages.common.HomePage;
```

## Benefits of New Structure

1. **Clear Organization** - Pages grouped by website menu structure
2. **Easy Navigation** - Find pages quickly by category
3. **Scalability** - Easy to add new pages in appropriate folders
4. **Domain Separation** - Clear distinction between public/auth pages
5. **Maintainability** - Changes to one category don't affect others

## Category Mapping to Website

| Website Menu | Folder | Description |
|--------------|--------|-------------|
| Product | `product/` | Product features and management pages |
| Resources | `resources/` | Documentation, community, and support |
| Company | `company/` | About company and team information |
| Prices | `pricing/` | Pricing and subscription plans |
| - | `common/` | Shared components (header, footer, home) |
| - | `auth/` | Login and registration pages |

## Future Structure (After Adding Authenticated Pages)

```
pages/
├── product/          # Public product pages
├── resources/        # Public resources
├── company/          # Public company info
├── pricing/          # Public pricing
├── common/           # Shared public components
├── auth/             # Authentication pages
│
└── account/          # NEW: Authenticated user pages
    ├── MainMenuPage.java
    ├── MyAccountPage.java
    └── DashboardPage.java
```

## Migration Summary

- ✅ 7 pages moved to `product/`
- ✅ 6 pages moved to `resources/`
- ✅ 3 pages moved to `company/`
- ✅ 1 page moved to `pricing/`
- ✅ 3 pages moved to `common/`
- ✅ 2 pages moved to `auth/`
- ✅ All test imports updated automatically
- ✅ Old files cleaned up

**Total: 22 pages reorganized**
