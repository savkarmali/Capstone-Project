import { Routes } from '@angular/router';
import { UploadProductComponent } from './admin/upload-product/upload-product.component';
import { ManageProductsComponent } from './admin/manage-products/manage-products.component';
import { ContactComponent } from './customer/contact/contact.component';
import { LocationsComponent } from './customer/locations/locations.component';
import { ReviewComponent } from './customer/review/review.component';
import { RegisterComponent } from './customer/account/register.component';
import { LoginComponent } from './customer/login/login.component';
import { ChangePasswordComponent } from './customer/change-password/change-password.component';
import { ShopComponent } from './customer/shop/shop.component';
import { ProductDetailComponent } from './customer/product-detail/product-detail.component';
import { CartComponent } from './customer/cart/cart.component';
import { CheckoutComponent } from './customer/checkout/checkout.component';
import { OrdersComponent } from './customer/orders/orders.component';
import { ReportsComponent } from './admin/reports/reports.component';
import { ContactMessagesComponent } from './admin/contact-messages/contact-messages.component';
import { ReviewListComponent } from './admin/review-list/review-list.component';
import { ManageLocationsComponent } from './admin/manage-locations/manage-locations.component';
import { InventoryReportComponent } from './admin/inventory-report/inventory-report.component';
import { CategorySalesReportComponent } from './admin/category-sales-report/category-sales-report.component';
import { ChartReportComponent } from './admin/chart-report/chart-report.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { HomeComponent } from './customer/home/home.component';

export const routes: Routes = [

  // Default route
  { path: '', redirectTo: 'account/login', pathMatch: 'full' },

  { path: 'customer/home', component: HomeComponent },
  { path: 'admin/dashboard', component: DashboardComponent },
  { path: 'admin/upload-product', component: UploadProductComponent },
  { path: 'admin/manage-products', component: ManageProductsComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'locations', component: LocationsComponent },
  { path: 'review', component: ReviewComponent },
  { path: 'account/register', component: RegisterComponent },
  { path: 'account/login', component: LoginComponent },
															 
  { path: 'account/change-password', component: ChangePasswordComponent },

			 
  { path: 'shop', component: ShopComponent },
  { path: 'shop/product/:id', component: ProductDetailComponent },
  { path: 'cart', component: CartComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'orders', component: OrdersComponent },

  { path: 'admin/reports', component: ReportsComponent },
  { path: 'admin/contact-messages', component: ContactMessagesComponent },
  { path: 'admin/reviews', component: ReviewListComponent },
  { path: 'admin/manage-locations', component: ManageLocationsComponent },
  { path: 'admin/reports/inventory', component: InventoryReportComponent },
  { path: 'admin/reports/category-sales', component: CategorySalesReportComponent },
  { path: 'admin/reports/charts', component: ChartReportComponent },

	// Invalid URL
  { path: '**', redirectTo: 'account/login' }

];
