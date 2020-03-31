/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

'use strict';

/* global getCurrentParticipant getParticipantRegistry getFactory emit */


/**
 * Initialize some test assets and participants useful for running a demo.
 */
async function setupDemo(setupDemo) {  // eslint-disable-line no-unused-vars

    const factory = getFactory();
    const NS = 'org.acme.pii';

    // create the Manufacturer
    const manufacturer = factory.newResource(NS, 'Manufacturer', 'manufacturer@email.com');
    const growerAddress = factory.newConcept(NS, 'Address');
    growerAddress.country = 'USA';
    manufacturer.address = growerAddress;

    // create the Wholesaler
    const wholesaler = factory.newResource(NS, 'Wholesaler', 'wholesale@email.com');
    const importerAddress = factory.newConcept(NS, 'Address');
    importerAddress.country = 'UK';
    importer.address = importerAddress;

    // create the Pharmacist
    const shipper = factory.newResource(NS, 'Pharmacist', 'pharmacist@email.com');
    const shipperAddress = factory.newConcept(NS, 'Address');
    shipperAddress.country = 'Panama';
    shipper.address = shipperAddress;

    // create the Patient
    const patient = factory.newResource(NS, 'Patient', 'patient@email.com');
    patient.address = shipperAddress;

    // create the contract
    const contract = factory.newResource(NS, 'Contract', 'CON_001');
    contract.manufacturer = factory.newRelationship(NS, 'Manufacturer', 'manufacturer@email.com');
    contract.wholesaler = factory.newRelationship(NS, 'Wholesaler', 'wholesale@email.com');
    contract.pharmacist = factory.newRelationship(NS, 'Pharmacist', 'pharmacist@email.com');
    contract.patient = factory.newRelationship(NS, 'Patient', 'patient@email.com');
    const tomorrow = setupDemo.timestamp;
    tomorrow.setDate(tomorrow.getDate() + 1);
    contract.arrivalDateTime = tomorrow; // the shipment has to arrive tomorrow
    contract.unitPrice = 0.5; // pay 50 cents per unit
    contract.minTemperature = 2; // min temperature for the cargo
    contract.maxTemperature = 10; // max temperature for the cargo
    contract.minPenaltyFactor = 0.2; // we reduce the price by 20 cents for every degree below the min temp
    contract.maxPenaltyFactor = 0.1; // we reduce the price by 10 cents for every degree above the max temp

    // create the shipment
    const shipment = factory.newResource(NS, 'Shipment', 'SHIP_001');
    shipment.type = 'CROCIN 250';
    shipment.status = 'IN_TRANSIT';
    shipment.unitCount = 5000;
    shipment.contract = factory.newRelationship(NS, 'Contract', 'CON_001');

    // add the manufacturer
    const growerRegistry = await getParticipantRegistry(NS + '.Manufacturer');
    await growerRegistry.addAll([manufacturer]);

    // add the impowholesalerrters
    const importerRegistry = await getParticipantRegistry(NS + '.Wholesaler');
    await importerRegistry.addAll([wholesaler]);

    // add the Pharmacist
    const shipperRegistry = await getParticipantRegistry(NS + '.Pharmacist');
    await shipperRegistry.addAll([shipper]);

    // add the Pharmacist
    const patientRegistry = await getParticipantRegistry(NS + '.Patient');
    await patientRegistry.addAll([patient]);

    // add the contracts
    const contractRegistry = await getAssetRegistry(NS + '.Contract');
    await contractRegistry.addAll([contract]);

    // add the shipments
    const shipmentRegistry = await getAssetRegistry(NS + '.Shipment');
    await shipmentRegistry.addAll([shipment]);
}