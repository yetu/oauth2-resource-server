package com.yetu.oauth2resource.utils

import com.yetu.oauth2resource.model.ValidationResponse

/**
 * mix in to your Play routes tests
 */
trait RoutesHelper {
  implicit def importedUrlBuilderConversion = AuthUrlBuilder.str2urlBuilder _

  //mey require renewal
  val correctToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiJ9.eyJzY29wZSI6ImJhciIsImlhdCI6MTQyMjQ2MDAyMCwiZXhwIjoxNzMzNTAwMDIwLCJhdWQiOiJ0ZXN0IiwiaXNzIjoiaHR0cHM6Ly9hdXRoLnlldHVkZXYuY29tIiwic3ViIjoic3ViamVjdC53aXRoLmRvdHMifQ.NNMlDrVNV3iLJCzuIA9dJcArZE69evas-z1H5mgwHGhOCoZmItXkzrZm_VbCY6EqaMYseAyF6agQddYNvaUZEEc1rq9vgN3XpeCiT6oVktjY6D-yu70SfeG1VSTpicyQX-VmzKKxj2Qn2DHJ_qF-Ck7mCY5Ki_qngMWYkxiV2Hq3Mfycz9zbBOWhg-aGPhn129cNAX_J0sswG2BGriLbxfnrvQ6PBMvlfTsXrqeLQP2dIaw0vXciK1lx8JvCkHQGurCV719PXDD1CatdFdBMlaVN7o-qUjkpRI0RHwAv0ppQOlO14et75DvyDwbMEEVvZvno0UnqNAb1uEQbhlqnQgny3jin8i3q--CQeiQXQEtLa2lIlvQYYSQVNOGddRi5KRMMLD9_DkFacQw-yV3srnSfHZGC_hjKtcT5taDP-BBlQcQWVdetoQOYwYWJTLgOZDfNBZjeTKcwiMWSqJSTMFhMrt9E9V4uWTRhg1sMtGqS-ZqLqmfPE3xLujbpSRAD-4Q3NKOcLAkloitpsmwD1bzfic1AVGfDR6Pl01Hya2Pq2tIquo-Jp98vBURIEVMPAubIvWWhlfNiTK04FhMMEETAKrC6btVValmIWOfS16IdUD1_QUE12tGt2Nw6PCdJp2gwwvfyYbOVfNcwnpUGbrGhZLETc37Lcl8wGIxiORw"
  val wrongToken = "wrong_access_token"

  val successfulValidationResponse = ValidationResponse(userUUID = Some("87656789-lkhghj-98678"),
    clientId = Some("com.yetu.someapp.id"),
    userEmail = Some("test@test.com"),
    scope = Some("basic"),
    exp = Some(1733500020),
    aud = Some("test"),
    iss = Some("https://auth.yetudev.com"),
    sub= Some("ubject.with.dots"))
}
