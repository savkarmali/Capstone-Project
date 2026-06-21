import { Routes } from '@angular/router';
import { UploadProductComponent } from './admin/upload-product/upload-product.component';
import { ManageProductsComponent } from './admin/manage-product/manage-products.component';
import { ContactComponent } from './customer/contact/contact.component';
import { LocationsComponent } from './customer/location/locations.component';

export const routes: Routes = [
  { path: '', redirectTo: 'admin/upload-product', pathMatch: 'full' },
  { path: 'admin/upload-product', component: UploadProductComponent },
  { path: 'admin/manage-products', component: ManageProductsComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'locations', component: LocationsComponent }
];
