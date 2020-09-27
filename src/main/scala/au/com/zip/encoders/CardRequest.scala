package au.com.zip
package encoders

case class CardRequestKey(customerId: String,
                          requestId: String,
                          cardNumber: String,
                          txnDateTime: String //yyyy-MM-dd
                         )

case class CardRequestValue(value: Double,
                            vendor: String
                           )

case class DailyCardGroupingKey(cardNumber: String,
                                txnDateTime: String
                               )

case class MonthlyCardGroupingKey(cardNumber: String,
                                  month: String
                                 )

case class GatewayResponse(requestId: String,
                           cardNumber: String,
                           txnDateTime: String, //yyyy-MM-dd
                           status: String,
                           reason: String
                          )

case class CardAuthorizationResponse(cardNumber: String,
                                     requestId: String,
                                     value: Double,
                                     vendor: String,
                                     status: String,
                                     reason: String
                                    )
