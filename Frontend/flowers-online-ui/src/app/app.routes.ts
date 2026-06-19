import { Routes } from '@angular/router';
import { UploadProductComponent } from './component/upload-product/upload-product.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'upload-product',
    pathMatch: 'full'
  },
  {
    path: 'upload-product',
    component: UploadProductComponent
  }
];