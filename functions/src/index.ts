import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

const firebase = admin.initializeApp();

export const emergencyListener = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .firestore
  .document("/teste_emergencia/{id}")
  .onCreate(async (snapshot, context) => {
    const data = snapshot.data();
    const docId = context.params.id;
    const querySnapshot = await firebase.firestore()
      .collection("dentists")
      .where("availability", "==", true).get();
    const tokens = querySnapshot.docs.map((doc)=>doc.data().fcmToken);
    const message ={
      data: {
        text: data.name,
        id: docId,
      },
      tokens: tokens,
    };
    try {
      await firebase.messaging().sendEachForMulticast(message);
      console.log("Enviada para ${response.successCount} dispositivos.");
    } catch (e) {
      console.error("Erro ao enviar a notificação", e);
    }
  });

export const setUser = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) =>{
    const {userId, ...userData} = data;
    await firebase.firestore()
      .collection("dentists")
      .doc(userId)
      .set(userData);
  });


export const emergencyListener2 = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .firestore
  .document("/teste_emergencia/{id}")
  .onUpdate(async (change, context) => {
    const newValue = change.after.data();
    const oldValue = change.before.data();
    const docId = context.params.id;
    const newStatus = newValue.status;
    const oldStatus = oldValue.status;

    if (newStatus === true && newStatus !== oldStatus) {
      const querySnapshot = await firebase.firestore()
        .collection("dentists")
        .where("availability", "==", true)
        .get();
      const tokens = querySnapshot.docs.map((doc)=>doc.data().fcmToken);
      const message ={
        data: {
          text: newValue.name,
          id: docId,
        },
        tokens: tokens,
      };
      try {
        await firebase.messaging().sendEachForMulticast(message);
        console.log("Enviada para ${response.successCount} dispositivos.");
      } catch (e) {
        console.error("Erro ao enviar a notificação", e);
      }
      // Restante do código...
    } else {
      console.log("O valor de status não foi modificado ");
    }
  });

