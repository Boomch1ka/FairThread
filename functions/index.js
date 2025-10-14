const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

exports.getProducts = functions.https.onRequest(async (req, res) => {
  const storeId = req.query.storeId;
  const category = req.query.category;

  try {
    const snapshot = await admin.firestore()
      .collection("products")
      .where("storeId", "==", storeId)
      .where("category", "==", category)
      .get();

    const products = snapshot.docs.map(doc => ({
      id: doc.id,
      ...doc.data()
    }));

    res.status(200).json(products);
  } catch (error) {
    res.status(500).send(error.message);
  }
});