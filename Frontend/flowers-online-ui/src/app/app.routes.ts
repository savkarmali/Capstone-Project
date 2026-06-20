import { Routes } from '@angular/router';
import { UploadProductComponent } from './component/upload-product/upload-product.component';
import { ManageProductsComponent } from './component/manage-products/manage-products.component';
import { ContactComponent } from './component/contact/contact.component';

export const routes: Routes = [
  { path: '', redirectTo: 'admin/upload-product', pathMatch: 'full' },
  { path: 'admin/upload-product', component: UploadProductComponent },
  { path: 'admin/manage-products', component: ManageProductsComponent },
  { path: 'contact', component: ContactComponent }
];
