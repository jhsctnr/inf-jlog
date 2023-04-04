import { products } from './index';

// 테스트 데이터 조회 API
function fetchProduct() {
  return products.get('products');
}

function fetchDept() {
  return products.get('depts');
}

function fetchFactory() {
  return products.get('factories');
}

export { fetchProduct, fetchDept, fetchFactory };
