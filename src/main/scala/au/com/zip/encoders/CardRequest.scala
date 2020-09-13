package au.com.zip
package encoders

case class CardRequestKey(customerId: String,
                          requestId: String,
                          cardNumber: String,
                          txnDateTime: String, //yyyyMMdd
                         )

case class CardRequestValue(value: Double,
                            vendor: String
                           )

case class CardGroupingKey(cardNumber: String,
                           txnDateTime: String
                          )

case class CardAuthorizationResponse(requestId: String,
                                     status: String,
                                     reason: String
                                    )
