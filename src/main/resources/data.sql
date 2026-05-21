-- Vider les tables dans le bon ordre (clés étrangères)
TRUNCATE TABLE table_comment CASCADE;
TRUNCATE TABLE table_ticket CASCADE;
TRUNCATE TABLE table_user CASCADE;

-- Réinitialiser les séquences d'ID
ALTER SEQUENCE table_user_id_seq RESTART WITH 1;
ALTER SEQUENCE table_ticket_id_seq RESTART WITH 1;
ALTER SEQUENCE table_comment_id_seq RESTART WITH 1;

-- Utilisateurs
INSERT INTO table_user (name, email, password, role) VALUES
                                                         ('Admin Dupont', 'admin1@helpdesk.fr', '$2a$10$BpZKNgmQEquXFgFvxXq1oubxkDY/HDir4BDXZXSc8tnhUbBfZdeOK', 'ADMIN'),
                                                         ('Admin Martin', 'admin2@helpdesk.fr', '$2a$10$BpZKNgmQEquXFgFvxXq1oubxkDY/HDir4BDXZXSc8tnhUbBfZdeOK', 'ADMIN'),
                                                         ('Tech Bernard', 'tech1@helpdesk.fr', '$2a$10$BpZKNgmQEquXFgFvxXq1oubxkDY/HDir4BDXZXSc8tnhUbBfZdeOK', 'TECHNICIEN'),
                                                         ('Tech Leroy', 'tech2@helpdesk.fr', '$2a$10$BpZKNgmQEquXFgFvxXq1oubxkDY/HDir4BDXZXSc8tnhUbBfZdeOK', 'TECHNICIEN'),
                                                         ('Tech Moreau', 'tech3@helpdesk.fr', '$2a$10$BpZKNgmQEquXFgFvxXq1oubxkDY/HDir4BDXZXSc8tnhUbBfZdeOK', 'TECHNICIEN');

-- Tickets
INSERT INTO table_ticket (title, description, client, priority, category, status, created_at, updated_at, technician_id) VALUES
                                                                                                                             ('Panne réseau salle de réunion', 'Le switch de la salle B est tombé en panne, plus aucune connexion possible.', 'Entreprise Martin', 'CRITIQUE', 'RESEAU', 'OUVERT', NOW() - INTERVAL '72 hours', NOW() - INTERVAL '72 hours', NULL),
                                                                                                                             ('Imprimante HS bureau comptabilité', 'L imprimante HP LaserJet ne répond plus après mise à jour Windows.', 'Cabinet Dubois', 'HAUTE', 'MATERIEL', 'EN_COURS', NOW() - INTERVAL '50 hours', NOW() - INTERVAL '50 hours', 3),
                                                                                                                             ('Virus détecté sur poste', 'Alerte antivirus sur le poste de Mme Petit, quarantaine effectuée.', 'Société Renard', 'CRITIQUE', 'SECURITE', 'EN_COURS', NOW() - INTERVAL '10 hours', NOW() - INTERVAL '10 hours', 4),
                                                                                                                             ('Mise à jour logiciel comptable', 'Le logiciel Sage nécessite une mise à jour vers la version 2024.', 'Cabinet Dubois', 'MOYENNE', 'LOGICIEL', 'OUVERT', NOW() - INTERVAL '5 hours', NOW() - INTERVAL '5 hours', NULL),
                                                                                                                             ('Écran noir au démarrage', 'Le PC du directeur affiche un écran noir après le logo Windows.', 'Entreprise Martin', 'HAUTE', 'MATERIEL', 'OUVERT', NOW() - INTERVAL '100 hours', NOW() - INTERVAL '100 hours', NULL),
                                                                                                                             ('Configuration VPN employés distants', 'Besoin de configurer le VPN pour 5 nouveaux employés en télétravail.', 'LogiTech SARL', 'MOYENNE', 'RESEAU', 'FERME', NOW() - INTERVAL '120 hours', NOW() - INTERVAL '24 hours', 3),
                                                                                                                             ('Sauvegarde automatique en échec', 'Le script de sauvegarde nocturne échoue depuis 3 jours.', 'Société Renard', 'HAUTE', 'LOGICIEL', 'EN_COURS', NOW() - INTERVAL '30 hours', NOW() - INTERVAL '30 hours', 5),
                                                                                                                             ('Accès refusé au serveur de fichiers', 'Plusieurs utilisateurs ne peuvent plus accéder au partage réseau.', 'BTP Girard', 'HAUTE', 'RESEAU', 'OUVERT', NOW() - INTERVAL '8 hours', NOW() - INTERVAL '8 hours', NULL),
                                                                                                                             ('Installation poste de travail', 'Nouveau salarié à équiper : installation Windows + logiciels métier.', 'LogiTech SARL', 'BASSE', 'LOGICIEL', 'FERME', NOW() - INTERVAL '200 hours', NOW() - INTERVAL '48 hours', 4),
                                                                                                                             ('Certificat SSL expiré', 'Le certificat SSL du site interne a expiré, alerte de sécurité navigateur.', 'BTP Girard', 'CRITIQUE', 'SECURITE', 'OUVERT', NOW() - INTERVAL '2 hours', NOW() - INTERVAL '2 hours', NULL)
    ON CONFLICT DO NOTHING;


-- Commentaires
INSERT INTO table_comment (content, created_at, author_id, ticket_id) VALUES
                                                                          ('Intervention planifiée demain matin 9h.', NOW() - INTERVAL '48 hours', 3, 2),
                                                                          ('Switch de remplacement commandé, livraison sous 24h.', NOW() - INTERVAL '40 hours', 1, 2),
                                                                          ('Poste isolé du réseau, analyse en cours.', NOW() - INTERVAL '9 hours', 4, 3),
                                                                          ('Malware identifié : trojan.generic. Nettoyage effectué.', NOW() - INTERVAL '5 hours', 4, 3),
                                                                          ('Script corrigé, sauvegarde relancée manuellement avec succès.', NOW() - INTERVAL '20 hours', 5, 7)
    ON CONFLICT DO NOTHING;