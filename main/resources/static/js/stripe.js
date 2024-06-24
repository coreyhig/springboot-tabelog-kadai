 const stripe = Stripe('pk_test_51OdROmKz85IpjOo66ScMLyjGFdaNmASXwXNQ9IsbTzRJZMsMxj23NQPinICzciDxRYqeu3QtIHAsdC2iVivszZWa00aFjxbTml');
 const paymentButton = document.querySelector('#paymentButton');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });